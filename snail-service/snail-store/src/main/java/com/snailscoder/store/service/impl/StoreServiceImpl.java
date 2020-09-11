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

import cn.hutool.core.lang.Assert;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.entity.Store;
import com.snailscoder.store.mapper.StoreMapper;
import com.snailscoder.store.service.ISellerService;
import com.snailscoder.store.service.IStoreService;
import com.snailscoder.upms.entity.User;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.feign.IUserClient;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺表 服务实现类
 *
 * @author snailscoder
 * @since 2020-08-20
 */
@Slf4j
@AllArgsConstructor
@Service
public class StoreServiceImpl extends BaseServiceImpl<StoreMapper, Store> implements IStoreService {

	private final IUserClient userClient;
	private final ISellerService sellerService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createStore(Store store) {
		log.info("创建店铺信息，创建人:{}", store.getCreateUser());
		R<UserInfo> result = userClient.userInfo(store.getCreateUser());
		if(result.isSuccess() && Func.isNotEmpty(result.getData())){
			Assert.notNull(result.getData().getUser());
			User user = result.getData().getUser();
			Seller seller = sellerService.getSeller(user.getId());
			if(Func.isNotEmpty(seller)){
				throw new ServiceException("用户已加入其它店铺，无法创建");
			}
			//店铺Logo默认为用户头像
			store.setStoreLogo(user.getAvatar());
			this.save(store);
			log.info("店铺创建成功，创建人:{}, 店铺ID:{}", store.getCreateUser(), store.getId());
			sellerService.addSeller(store.getId(), user, user.getId());
			return true;
		}else {
			throw new ServiceException("用户信息不存在");
		}
	}

	@Override
	@Cacheable(cacheNames = CacheNames.USER_STORE_KEY, key = "#userId", unless="#result == null")
	public Store getStoreByUserId(Long userId) {
		Seller seller = sellerService.getSeller(userId);
		if(Func.isEmpty(seller)){
			return null;
		}
		return this.getById(seller.getStoreId());
	}
}
