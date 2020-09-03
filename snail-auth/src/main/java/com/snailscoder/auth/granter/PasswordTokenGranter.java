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
package com.snailscoder.auth.granter;

import com.snailscoder.auth.enums.UserTypeEnum;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.feign.IUserClient;
import lombok.AllArgsConstructor;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.DigestUtil;
import com.snailscoder.core.tool.utils.Func;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author snailscoder
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String account = tokenParameter.getAccount();
		String password = tokenParameter.getPassword();
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getUserType();
			R<UserInfo> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(UserTypeEnum.WEB.getName())) {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			} else if (userType.equals(UserTypeEnum.APP.getName())) {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			} else {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			}
			userInfo = result.isSuccess() ? result.getData() : null;
		}
		return userInfo;
	}

}
