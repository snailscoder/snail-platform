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
package com.snailscoder.store.wrapper;

import com.snailscoder.store.entity.Product;
import com.snailscoder.store.vo.ProductVO;
import com.snailscoder.core.mp.support.BaseEntityWrapper;
import com.snailscoder.core.tool.utils.BeanUtil;

/**
 * Product包装类,返回视图层所需的字段
 *
 * @author snailscoder
 */
public class ProductWrapper extends BaseEntityWrapper<Product, ProductVO> {

	public static ProductWrapper build() {
		return new ProductWrapper();
	}

	@Override
	public ProductVO entityVO(Product product) {
		return BeanUtil.copy(product, ProductVO.class);
	}

}
