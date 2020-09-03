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
package com.snailscoder.store.wrapper;

import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.vo.SellerVO;
import com.snailscoder.core.mp.support.BaseEntityWrapper;
import com.snailscoder.core.tool.utils.BeanUtil;

/**
 * 销售员表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-24
 */
public class SellerWrapper extends BaseEntityWrapper<Seller, SellerVO>  {

    public static SellerWrapper build() {
        return new SellerWrapper();
    }

	@Override
	public SellerVO entityVO(Seller seller) {
		SellerVO sellerVO = BeanUtil.copy(seller, SellerVO.class);

		return sellerVO;
	}

}
