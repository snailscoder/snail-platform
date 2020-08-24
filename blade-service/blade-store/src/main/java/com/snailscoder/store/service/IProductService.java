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
package com.snailscoder.store.service;

import com.snailscoder.core.mybatis.base.BaseService;
import com.snailscoder.store.entity.Product;
import com.snailscoder.store.vo.ProductVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户表 服务类
 *
 * @author snailscoder
 * @since 2020-08-19
 */
public interface IProductService extends BaseService<Product> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param product
	 * @return
	 */
	IPage<ProductVO> selectProductPage(IPage<ProductVO> page, ProductVO product);

}
