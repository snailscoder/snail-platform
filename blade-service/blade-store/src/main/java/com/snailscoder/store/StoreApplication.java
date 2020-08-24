package com.snailscoder.store;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Demo启动器
 *
 * @author snailscoder
 */
@SpringCloudApplication
@EnableFeignClients(CommonConstant.BASE_PACKAGES)
public class StoreApplication {

	public static void main(String[] args) {
		BladeApplication.run(CommonConstant.APPLICATION_STORE_NAME, StoreApplication.class, args);
	}

}

