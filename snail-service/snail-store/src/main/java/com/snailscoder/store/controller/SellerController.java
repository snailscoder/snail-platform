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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.core.boot.ctrl.SnailController;
import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.mp.support.Query;
import com.snailscoder.core.secure.LoginUser;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.service.ISellerService;
import com.snailscoder.store.vo.SellerVO;
import com.snailscoder.store.wrapper.SellerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 销售员表 控制器
 *
 * @author Blade
 * @since 2020-08-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/seller")
@Api(value = "销售员表", tags = "销售员表接口")
public class SellerController extends SnailController {

	private final ISellerService sellerService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入seller")
	public R<SellerVO> detail(Long id) {
		Seller detail = sellerService.getById(id);
		return R.data(SellerWrapper.build().entityVO(detail));
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail/user")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入seller")
	public R<SellerVO> detail(LoginUser loginUser) {
		if(Func.isNotEmpty(loginUser)){
			Seller detail = sellerService.getSeller(loginUser.getUserId());
			return R.data(SellerWrapper.build().entityVO(detail));
		}else {
			return R.data(null);
		}
	}

	/**
	* 分页 销售员表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入seller")
	public R<IPage<SellerVO>> list(Seller seller, Query query) {
		QueryWrapper<Seller> queryWrapper = Condition.getQueryWrapper(seller);
		IPage<Seller> pages = sellerService.page(Condition.getPage(query), queryWrapper);
		return R.data(SellerWrapper.build().pageVO(pages));
	}

	/**
	* 新增 销售员表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入seller")
	public R save(@Valid @RequestBody Seller seller) {
		return R.status(sellerService.save(seller));
	}

	/**
	* 修改 销售员表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入seller")
	public R update(@Valid @RequestBody Seller seller) {
		return R.status(sellerService.updateById(seller));
	}

	/**
	* 新增或修改 销售员表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入seller")
	public R submit(@Valid @RequestBody Seller seller) {
		return R.status(sellerService.saveOrUpdate(seller));
	}


	/**
	* 删除 销售员表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(sellerService.deleteLogic(Func.toLongList(ids)));
	}


}
