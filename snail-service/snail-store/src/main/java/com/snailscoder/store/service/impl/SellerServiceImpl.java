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
package com.snailscoder.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.boot.ctrl.SnailController;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.tool.constant.SnailConstant;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.store.constant.AppConstant;
import com.snailscoder.store.entity.Seller;
import com.snailscoder.store.mapper.SellerMapper;
import com.snailscoder.store.service.ISellerService;
import com.snailscoder.upms.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售员表 服务实现类
 *
 * @author SnailCoder
 * @since 2020-08-24
 */
@Slf4j
@Service
public class SellerServiceImpl extends BaseServiceImpl<SellerMapper, Seller> implements ISellerService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Seller addSeller(Long storeId, User user, Long inviter) {
		log.info("店铺添加销售员，店铺ID:{},用户ID:{}", storeId, user.getId());
		Seller seller = this.getSeller(storeId, user.getId());
		if(Func.isEmpty(seller)){
			seller = new Seller();
			seller.setUserId(user.getId());
			seller.setStoreId(storeId);
			seller.setInviter(inviter);
			seller.setShortName(user.getName());
			seller.setLevel(AppConstant.DEFAULT_SELLER_LEVEL);
			seller.setDiscount(AppConstant.DEFAULT_SELLER_DISCOUNT);
			this.save(seller);
			log.info("店铺销售员添加成功，店铺ID:{},用户ID:{}", seller.getStoreId(), seller.getUserId());
		}else {
			log.info("店铺销售员已存在，暂不处理，店铺ID:{},用户ID:{}", seller.getStoreId(), seller.getUserId());
		}
		return seller;
	}

	@Override
	public Seller getSeller(Long storeId, Long userId) {
		LambdaQueryWrapper<Seller> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Seller::getStoreId, storeId);
		wrapper.eq(Seller::getUserId, userId);
		wrapper.eq(Seller::getStatus, SnailConstant.DB_STATUS_NORMAL);
		return this.getOne(wrapper);
	}

	@Override
	public Seller getSeller(Long userId) {
		LambdaQueryWrapper<Seller> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Seller::getUserId, userId);
		wrapper.eq(Seller::getStatus, SnailConstant.DB_STATUS_NORMAL);
		return this.getOne(wrapper);
	}

}
