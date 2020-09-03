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
package com.snailscoder.upms.feign;

import com.snailscoder.core.tool.api.R;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.entity.UserOauth;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author snailscoder
 */
@Component
public class IUserClientFallback implements IUserClient {

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userInfo(String account, String password) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userAuthInfo(UserOauth userOauth) {
		return R.fail("未获取到账号信息");
	}

}
