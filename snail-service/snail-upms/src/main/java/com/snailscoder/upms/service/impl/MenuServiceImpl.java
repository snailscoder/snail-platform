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
import com.snailscoder.core.secure.LoginUser;
import com.snailscoder.core.tool.node.ForestNodeMerger;
import com.snailscoder.core.tool.support.Kv;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.StringUtil;
import com.snailscoder.upms.dto.MenuDTO;
import com.snailscoder.upms.entity.Menu;
import com.snailscoder.upms.entity.RoleMenu;
import com.snailscoder.upms.mapper.MenuMapper;
import com.snailscoder.upms.service.IMenuService;
import com.snailscoder.upms.service.IRoleMenuService;
import com.snailscoder.upms.vo.MenuVO;
import com.snailscoder.upms.wrapper.MenuWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author snailscoder
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

	IRoleMenuService roleMenuService;

	@Override
	public List<MenuVO> routes(String roleId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Menu::getCategory,1);
		List<Menu> allMenus = this.list(wrapper);

		List<Menu> roleMenus = baseMapper.roleMenu(Func.toLongList(roleId));
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = baseMapper.buttons(Func.toLongList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<MenuVO> tree() {
		return MenuWrapper.build().listNodeVO(this.list());
	}

	@Override
	public List<MenuVO> grantTree(LoginUser user) {
		return ForestNodeMerger.merge(baseMapper.grantTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<Kv> authRoutes(LoginUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toLongList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.init().set(route.getPath(), Kv.init().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

}
