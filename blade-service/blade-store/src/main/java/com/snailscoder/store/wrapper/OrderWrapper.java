/**
 * Copyright (c) 2018-2028, huaxin (huaxin803@gmail.com).
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

import com.snailscoder.store.entity.Order;
import com.snailscoder.store.vo.OrderVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;

/**
 * 销售订单包装类,返回视图层所需的字段
 *
 * @author snailscoder
 * @since 2020-08-20
 */
public class OrderWrapper extends BaseEntityWrapper<Order, OrderVO>  {

    public static OrderWrapper build() {
        return new OrderWrapper();
    }

	@Override
	public OrderVO entityVO(Order order) {
		OrderVO orderVO = BeanUtil.copy(order, OrderVO.class);

		return orderVO;
	}

}
