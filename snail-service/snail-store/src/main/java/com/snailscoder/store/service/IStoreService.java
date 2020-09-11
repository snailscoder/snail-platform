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
package com.snailscoder.store.service;

import com.snailscoder.core.mp.base.BaseService;
import com.snailscoder.store.entity.Store;

/**
 * 店铺表 服务类
 *
 * @author snailscoder
 * @since 2020-08-20
 */
public interface IStoreService extends BaseService<Store> {

	/**
	 * 创建店铺
	 * @param store
	 * @return
	 */
	boolean createStore(Store store);

	/**
	 * 根据用户ID查询所属店铺
	 * @param userId
	 * @return
	 */
	Store getStoreByUserId(Long userId);
}
