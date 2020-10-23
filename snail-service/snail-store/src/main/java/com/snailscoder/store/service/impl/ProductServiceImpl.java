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
package com.snailscoder.store.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.dto.ProductDTO;
import com.snailscoder.store.dto.ProductSkuDTO;
import com.snailscoder.store.entity.Product;
import com.snailscoder.store.entity.ProductSku;
import com.snailscoder.store.mapper.ProductMapper;
import com.snailscoder.store.service.IProductService;
import com.snailscoder.store.service.IProductSkuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品表 服务实现类
 *
 * @author snailscoder
 * @since 2020-08-19
 */
@AllArgsConstructor
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements IProductService {

	private final IProductSkuService skuService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setMainImg(productDTO.getMainImg());
		product.setIntro(productDTO.getIntro());
		product.setStoreId(productDTO.getStoreId());
		this.save(product);
		if(CollectionUtil.isEmpty(productDTO.getSkuList())){
			throw new ServiceException("商品不能没有商品规格");
		}
		List<ProductSkuDTO> collect = productDTO.getSkuList().stream().filter(key -> Func.isNotBlank(key.getSkuName())).collect(Collectors.toList());
		if(CollectionUtil.isEmpty(collect)){
			throw new ServiceException("商品不能没有商品规格");
		}
		collect.forEach(sku->{
			ProductSku productSku = new ProductSku();
			productSku.setAgentPrice(sku.getAgentPrice());
			productSku.setCostPrice(sku.getCostPrice());
			productSku.setSkuName(sku.getSkuName());
			productSku.setRetailPrice(sku.getRetailPrice());
			productSku.setProductId(product.getId());
			productSku.setStoreId(product.getStoreId());
			skuService.save(productSku);
		});
		return true;
	}
}
