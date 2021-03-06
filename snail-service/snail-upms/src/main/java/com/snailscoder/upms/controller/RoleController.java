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
package com.snailscoder.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.core.boot.ctrl.SnailController;
import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.secure.LoginUser;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.node.INode;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.upms.entity.Role;
import com.snailscoder.upms.service.IRoleService;
import com.snailscoder.upms.vo.GrantVO;
import com.snailscoder.upms.vo.RoleVO;
import com.snailscoder.upms.wrapper.RoleWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author snailscoder
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends SnailController {

	private final IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list(queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree() {
		return R.data(roleService.tree());
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R<Void> submit(@Valid @RequestBody Role role) {
		return R.status(roleService.saveOrUpdate(role));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Void> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置菜单权限
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R<Void> grant(@RequestBody GrantVO grantVO) {
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds());
		return R.status(temp);
	}

}
