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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.mp.support.Query;
import com.snailscoder.core.secure.LoginUser;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.dto.ProductDTO;
import com.snailscoder.store.dto.ProductSkuDTO;
import com.snailscoder.store.entity.Product;
import com.snailscoder.store.entity.Store;
import com.snailscoder.store.service.IProductService;
import com.snailscoder.store.service.IStoreService;
import com.snailscoder.store.vo.ProductVO;
import com.snailscoder.store.wrapper.ProductWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: snailscoder
 * @date: 2020/8/19 下午7:22
 */
@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Api(value = "店铺商品", tags = "商品接口")
public class ProductController extends BaseStoreController implements CacheNames {

	private final IProductService productService;
	private final IStoreService storeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<ProductVO> detail(@RequestParam("id") Long id) {
		Product product = productService.getById(id);
		return R.data(ProductWrapper.build().entityVO(product));
	}

	/**
	 * 分页 用户表
	 */
	@GetMapping("/list/store")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入product")
	public R<IPage<ProductVO>> storeProductList(Product product, Query query, LoginUser loginUser) {
		Store store = storeService.getStoreByUserId(loginUser.getUserId());
		product.setStoreId(store.getId());
		IPage<Product> pages = productService.page(Condition.getPage(query), Condition.getQueryWrapper(product));
		return R.data(ProductWrapper.build().pageVO(pages));
	}

	/**
	 * 分页 用户表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入product")
	public R<IPage<ProductVO>> list(Product product, Query query) {
		IPage<Product> pages = productService.page(Condition.getPage(query), Condition.getQueryWrapper(product));
		return R.data(ProductWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入商品详细信息")
	public R<Void> save(@RequestBody ProductDTO product, LoginUser loginUser) {
		Store store = storeService.getStoreByUserId(loginUser.getUserId());
		Assert.notNull(store);
		Assert.notNull(product.getSkuList());
		List<ProductSkuDTO> collect = product.getSkuList().stream().filter(key -> Func.isNotBlank(key.getSkuName())).collect(Collectors.toList());
		Assert.notNull(collect);
		product.setSkuList(collect);
		product.setStoreId(store.getId());
		return R.status(productService.addProduct(product));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入商品详细信息")
	public R<Void> update(@RequestBody Product product) {
		return R.status(productService.updateById(product));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public R<Void> remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		return R.status(productService.deleteLogic(Func.toLongList(ids)));
	}

}
