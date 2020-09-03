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
import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.vo.SellerVO;
import com.snailscoder.store.wrapper.SellerWrapper;
import com.snailscoder.store.service.ISellerService;
import com.snailscoder.core.boot.ctrl.BladeController;
import java.util.List;

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
public class SellerController extends BaseStoreController {

	private ISellerService sellerService;

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
	* 分页 销售员表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入seller")
	public R<IPage<SellerVO>> list(Seller seller, Query query, BladeUser bladeUser) {
		QueryWrapper<Seller> queryWrapper = Condition.getQueryWrapper(seller);
		Long storeId = getStoreId(bladeUser);
		IPage<Seller> pages = sellerService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Seller::getStoreId, storeId) : queryWrapper);
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
