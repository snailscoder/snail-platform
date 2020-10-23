/*
 * Copyright (c) 2018-2028, snailscoder (huaxin803@gmail.com).
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
package com.snailscoder.upms.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snailscoder.core.mp.base.BaseService;
import com.snailscoder.upms.dto.UserOauthEncryDTO;
import com.snailscoder.upms.entity.User;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.entity.UserOauth;
import com.snailscoder.upms.excel.UserExcel;

import java.util.List;

/**
 * 服务类
 *
 * @author snailscoder
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 * @param user
	 * @return
	 */
	boolean submit(User user);
	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String account, String password);

	/**
	 * 用户信息
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);

	/**
	 * 导入用户数据
	 *
	 * @param data
	 * @return
	 */
	void importUser(List<UserExcel> data);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 更新Oauth并注册用户
	 * @param encryDTO
	 * @return
	 */
	boolean registerOauth(UserOauthEncryDTO encryDTO);
}
