/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snailscoder.upms.service.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.snailscoder.common.constant.CommonConstant;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.tool.utils.BeanUtil;
import com.snailscoder.core.tool.utils.DigestUtil;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.StringUtil;
import com.snailscoder.upms.entity.Role;
import com.snailscoder.upms.dto.UserOauthEncryDTO;
import com.snailscoder.upms.entity.User;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.entity.UserOauth;
import com.snailscoder.upms.excel.UserExcel;
import com.snailscoder.upms.mapper.UserMapper;
import com.snailscoder.upms.service.IRoleService;
import com.snailscoder.upms.service.IUserOauthService;
import com.snailscoder.upms.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author snailscoder
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private static final String GUEST_NAME = "guest";
	private static final String MINUS_ONE = "-1";

	@Resource
	private WxMaService wxMaService;
	@Resource
	IUserOauthService userOauthService;
	@Resource
	IRoleService roleService;

	@Override
	public boolean submit(User user) {
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getAccount, user.getAccount()));
		if (cnt > 0) {
			throw new ApiException("当前用户已存在!");
		}
		return saveOrUpdate(user);
	}

	/**
	 * 根据用户ID查询用户及角色信息
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo userInfo(Long userId) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.selectById(userId);
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			userInfo.setRoles(getRoleAlias(user.getRoleId()));
		}
		return userInfo;
	}

	private List<String> getRoleAlias(String roleId){
		List<Role> roleList = roleService.getRoleList(roleId);
		return roleList.stream().filter(Objects::nonNull).map(Role::getRoleAlias).collect(Collectors.toList());
	}

	/**
	 * 根据账号密码查询用户信息及角色
	 * @param account
	 * @param password
	 * @return
	 */
	@Override
	public UserInfo userInfo(String account, String password) {
		User user = this.getUser(account, password);
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			userInfo.setRoles(getRoleAlias(user.getRoleId()));
		}
		return userInfo;
	}

	/**
	 * 根据账号密码查询用户
	 * @param account
	 * @param password
	 * @return
	 */
	private User getUser(String account, String password){
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getAccount,account);
		wrapper.eq(User::getPassword,password);
		return this.getOne(wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = null;
		if(Func.isNotEmpty(userOauth.getId())){
			uo = userOauthService.getById(userOauth.getId());
		}else {
			uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		}
		if(Func.isEmpty(uo) && Func.isNotEmpty(userOauth.getUnionId())){
			UserOauth unionUo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUnionId, userOauth.getUnionId()));
			if(Func.isNotEmpty(unionUo)){
				userOauth.setUserId(unionUo.getUserId());
			}
		}
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				userOauthService.save(userOauth);
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				uo.setAccessToken(userOauth.getAccessToken());
				userOauthService.updateById(uo);
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUuid());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(LocalDateTime.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
		User user = getById(userId);
		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
	}

	@Override
	public List<String> getRoleName(String roleIds) {
		List<Role> roleList = roleService.getRoleList(roleIds);
		return roleList.stream().filter(Objects::nonNull).map(Role::getRoleName).collect(Collectors.toList());
	}

	@Override
	public void importUser(List<UserExcel> data) {
		data.forEach(userExcel -> {
			User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
			// 设置角色ID
			user.setRoleId(roleService.getRoleIds(userExcel.getRoleName()));
			// 设置默认密码
			user.setPassword(CommonConstant.DEFAULT_PASSWORD);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			List<String> nameList = roleService.getRoleList(user.getRoleId()).stream().map(Role::getRoleName).collect(Collectors.toList());
			user.setRoleName(StringUtil.join(nameList));
		});
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ApiException("第三方登陆信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(MINUS_ONE);
		boolean userTemp = this.submit(user);
		userOauth.setUserId(user.getId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerOauth(UserOauthEncryDTO encryDTO) {
		UserOauth userOauth = userOauthService.getById(encryDTO.getId());
		WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(userOauth.getAccessToken(),encryDTO.getEncryptedData(),encryDTO.getIv());
		convertMaUser2UserOauth(wxMaUserInfo,userOauth);
		userOauthService.saveOrUpdate(userOauth);
		User user = new User();
		user.setAccount(userOauth.getUuid());
		user.setName(userOauth.getNickname());
		user.setRealName(userOauth.getUsername());
		return this.registerGuest(user,userOauth.getId());
	}

	/**
	 * 小程序用户信息转换
	 * @param wxMaUserInfo
	 */
	private void convertMaUser2UserOauth(WxMaUserInfo wxMaUserInfo, UserOauth userOauth){
		userOauth.setUnionId(wxMaUserInfo.getUnionId());
		userOauth.setUsername(wxMaUserInfo.getNickName());
		userOauth.setNickname(wxMaUserInfo.getNickName());
		userOauth.setGender(wxMaUserInfo.getGender());
		userOauth.setAvatar(wxMaUserInfo.getAvatarUrl());
		userOauth.setLocation(wxMaUserInfo.getCountry() + "-" + wxMaUserInfo.getProvince() + "-" + wxMaUserInfo.getCity());
	}

}
