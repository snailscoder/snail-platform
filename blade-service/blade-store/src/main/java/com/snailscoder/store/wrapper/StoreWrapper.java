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

import com.snailscoder.store.entity.Store;
import com.snailscoder.store.vo.StoreVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;

/**
 * Store包装类,返回视图层所需的字段
 *
 * @author snailscoder
 */
public class StoreWrapper extends BaseEntityWrapper<Store, StoreVO> {

	public static StoreWrapper build() {
		return new StoreWrapper();
	}

	@Override
	public StoreVO entityVO(Store store) {
		return BeanUtil.copy(store, StoreVO.class);
	}

}
