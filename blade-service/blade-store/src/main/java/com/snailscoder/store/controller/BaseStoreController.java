package com.snailscoder.store.controller;

import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.secure.BladeUser;

/**
 * @author: snailscoder
 * @date: 2020/8/24 上午12:43
 */
public class BaseStoreController extends BladeController {



	/**
	 * 获取用户所属店铺ID
	 * @param bladeUser
	 * @return
	 */
	Long getStoreId(BladeUser bladeUser){
		return Long.valueOf(bladeUser.getTenantId());
	}
}
