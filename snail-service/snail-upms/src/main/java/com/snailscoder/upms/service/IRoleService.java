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

import com.snailscoder.core.mp.base.BaseService;
import com.snailscoder.upms.entity.Role;
import com.snailscoder.upms.vo.RoleVO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类
 *
 * @author snailscoder
 */
public interface IRoleService extends BaseService<Role> {

	/**
	 * 树形结构
	 * @return
	 */
	List<RoleVO> tree();

	/**
	 * 权限配置
	 *
	 * @param roleIds 角色id集合
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds);

	/**
	 * 根据角色名字获取角色ID字符串
	 * @param roleNames
	 * @return
	 */
	String getRoleIds(String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<Role> getRoleList(String roleIds);

}
