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

package com.snailscoder.store;

import com.snailscoder.common.constant.CommonConstant;
import com.snailscoder.core.launch.StartApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Demo启动器
 *
 * @author snailscoder
 */
@MapperScan("com.snailscoder.**.mapper.**")
@SpringCloudApplication
@EnableFeignClients(CommonConstant.BASE_PACKAGES)
public class StoreApplication {

	public static void main(String[] args) {
		StartApplication.run(CommonConstant.APPLICATION_STORE_NAME, StoreApplication.class, args);
	}

}

