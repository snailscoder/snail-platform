/**
 * Copyright (c) 2018-2028, huaxin (huaxin803@gmail.com).
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
package com.snailscoder.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snailscoder.store.entity.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.mp.support.Query;
import com.snailscoder.core.secure.BladeUser;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.constant.BladeConstant;
import com.snailscoder.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snailscoder.store.vo.StoreVO;
import com.snailscoder.store.wrapper.StoreWrapper;
import com.snailscoder.store.service.IStoreService;
import com.snailscoder.core.boot.ctrl.BladeController;
import java.util.List;

/**
 * 店铺表 控制器
 *
 * @author snailscoder
 * @since 2020-08-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/store")
@Api(value = "店铺表", tags = "店铺表接口")
public class StoreController extends BladeController {

	private IStoreService storeService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入store")
	public R<StoreVO> detail(@RequestParam("id") Long id) {
		Store store = storeService.getById(id);
		return R.data(StoreWrapper.build().entityVO(store));
	}

	/**
	* 分页 店铺表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入store")
	public R<IPage<StoreVO>> list(Store store, Query query) {
		IPage<Store> pages = storeService.page(Condition.getPage(query), Condition.getQueryWrapper(store));
		return R.data(StoreWrapper.build().pageVO(pages));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public R<List<Store>> select(Store store, BladeUser bladeUser) {
		QueryWrapper<Store> queryWrapper = Condition.getQueryWrapper(store);
		List<Store> list = storeService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Store::getId, store.getId()) : queryWrapper);
		return R.data(list);
	}

	/**
	* 自定义分页 店铺表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入store")
	public R<IPage<StoreVO>> page(StoreVO store, Query query) {
		IPage<StoreVO> pages = storeService.selectStorePage(Condition.getPage(query), store);
		return R.data(pages);
	}

	/**
	* 新增 店铺表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入store")
	public R save(@Valid @RequestBody Store store) {
		return R.status(storeService.save(store));
	}

	/**
	* 修改 店铺表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入store")
	public R update(@Valid @RequestBody Store store) {
		return R.status(storeService.updateById(store));
	}

	/**
	* 新增或修改 店铺表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入store")
	public R submit(@Valid @RequestBody Store store) {
		return R.status(storeService.saveOrUpdate(store));
	}


	/**
	* 删除 店铺表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(storeService.deleteLogic(Func.toLongList(ids)));
	}


}
