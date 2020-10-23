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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.entity.ProductSku;
import com.snailscoder.store.service.IProductSkuService;
import com.snailscoder.store.vo.ProductSkuVO;
import com.snailscoder.store.wrapper.ProductSkuWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author: snailscoder
 * @date: 2020/8/19 下午7:22
 */
@RestController
@RequestMapping("/sku")
@AllArgsConstructor
@Api(value = "店铺商品SKU", tags = "商品SKU接口")
public class ProductSkuController extends BaseStoreController implements CacheNames {

	private final IProductSkuService skuService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<ProductSkuVO> detail(@RequestParam("id") Long id) {
		ProductSku sku = skuService.getById(id);
		return R.data(ProductSkuWrapper.build().entityVO(sku));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入商品详细信息")
	public R save(@RequestBody ProductSku sku) {
		return R.status(skuService.save(sku));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入商品详细信息")
	public R update(@RequestBody ProductSku sku) {
		return R.status(skuService.updateById(sku));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入商品详细信息")
	public R submit(@RequestBody ProductSku sku) {
		return R.status(skuService.saveOrUpdate(sku));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = skuService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

}
