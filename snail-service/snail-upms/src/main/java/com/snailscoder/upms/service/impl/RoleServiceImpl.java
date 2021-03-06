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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.secure.utils.SecureUtil;
import com.snailscoder.core.tool.constant.RoleConstant;
import com.snailscoder.core.tool.node.ForestNodeMerger;
import com.snailscoder.core.tool.utils.CollectionUtil;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.upms.entity.Role;
import com.snailscoder.upms.entity.RoleMenu;
import com.snailscoder.upms.mapper.RoleMapper;
import com.snailscoder.upms.service.IRoleMenuService;
import com.snailscoder.upms.service.IRoleService;
import com.snailscoder.upms.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author snailscoder
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

	IRoleMenuService roleMenuService;

	@Override
	public List<RoleVO> tree() {
		String userRole = SecureUtil.getUserRole();
		String excludeRole = null;
		if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN)) {
			excludeRole = RoleConstant.ADMIN;
		}
		String finalExcludeRole = excludeRole;
		List<RoleVO> collect = this.list().stream().filter(key -> !key.getRoleAlias().equals(finalExcludeRole)).map(role -> {
			RoleVO node = new RoleVO();
			node.setId(role.getId());
			node.setParentId(role.getParentId());
			node.setRoleName(role.getRoleName());
			node.setRoleAlias(role.getRoleAlias());
			return node;
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	@Override
	public boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds) {
		// 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenus.add(roleMenu);
		}));
		// 新增配置
		return roleMenuService.saveBatch(roleMenus);
	}

	@Override
	public String getRoleIds(String roleNames) {
		List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().in(Role::getRoleName, Func.toStrList(roleNames)));
		if (roleList != null && roleList.size() > 0) {
			return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<Role> getRoleList(String roleIds) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(Role::getId,Func.toLongList(roleIds));
		return this.list(wrapper);
	}
}
