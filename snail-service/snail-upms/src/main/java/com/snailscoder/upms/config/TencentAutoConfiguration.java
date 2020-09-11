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

package com.snailscoder.upms.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: snailscoder
 * @date: 2020/9/9 下午5:59
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({TencentProperties.class})
public class TencentAutoConfiguration {
	private final TencentProperties properties;

	@Bean
	public COSClient cosClient(){
		COSCredentials cred = cosCredentials();
		ClientConfig clientConfig = new ClientConfig(new Region(properties.getReginName()));
		return new COSClient(cred, clientConfig);
	}

	@Bean
	public ClientConfig clientConfig(){
		return new ClientConfig(new Region(properties.getReginName()));
	}

	@Bean
	public COSCredentials cosCredentials(){
		return new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
	}
}
