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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snailscoder.core.mybatis.base.ISuperService;
import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.vo.SellerVO;

/**
 * 销售员表 服务类
 *
 * @author Blade
 * @since 2020-08-24
 */
public interface ISellerService extends ISuperService<Seller> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param seller
	 * @return
	 */
	IPage<SellerVO> selectSellerPage(IPage<SellerVO> page, SellerVO seller);

}
