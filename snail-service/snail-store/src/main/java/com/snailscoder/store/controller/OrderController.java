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
package com.snailscoder.store.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.mp.support.Query;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.entity.Order;
import com.snailscoder.store.service.IOrderService;
import com.snailscoder.store.vo.OrderVO;
import com.snailscoder.store.wrapper.OrderWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 销售订单 控制器
 *
 * @author snailscoder
 * @since 2020-08-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Api(value = "销售订单", tags = "销售订单接口")
public class OrderController extends BaseStoreController {

	private final IOrderService orderService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入order")
	public R<OrderVO> detail(Order order) {
		Order detail = orderService.getOne(Condition.getQueryWrapper(order));
		return R.data(OrderWrapper.build().entityVO(detail));
	}

	/**
	* 分页 销售订单
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入order")
	public R<IPage<OrderVO>> list(Order order, Query query) {
		IPage<Order> pages = orderService.page(Condition.getPage(query), Condition.getQueryWrapper(order));
		return R.data(OrderWrapper.build().pageVO(pages));
	}

	/**
	* 新增 销售订单
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入order")
	public R save(@Valid @RequestBody Order order) {
		return R.status(orderService.save(order));
	}

	/**
	* 修改 销售订单
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入order")
	public R update(@Valid @RequestBody Order order) {
		return R.status(orderService.updateById(order));
	}

	/**
	* 新增或修改 销售订单
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入order")
	public R submit(@Valid @RequestBody Order order) {
		return R.status(orderService.saveOrUpdate(order));
	}


	/**
	* 删除 销售订单
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(orderService.deleteLogic(Func.toLongList(ids)));
	}


}
