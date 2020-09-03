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
package com.snailscoder.gateway.config;

import com.snailscoder.core.launch.constant.AppConstant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由配置类
 *
 * @author snailscoder
 */
@Data
@RefreshScope
@ConfigurationProperties("snail")
public class AppProperties {

	private Secure secure;
	private Document document;

	@Setter
	@Getter
	public static class Secure{
		/**
		 * 放行API集合
		 */
		private List<String> skipUrl = new ArrayList<>();
	}

	/**
	 * 文档配置
	 */
	@Setter
	@Getter
	public static class Document{
		private List<RouteResource> routes = new ArrayList<>();
	}


	@Data
	public static class RouteResource {

		/**
		 * 文档名
		 */
		private String name;

		/**
		 * 文档所在服务地址
		 */
		private String location;

		/**
		 * 文档版本
		 */
		private String version = AppConstant.APPLICATION_VERSION;

	}

}
