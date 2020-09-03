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
import com.snailscoder.auth.utils.TokenUtil;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.*;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author snailscoder
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private IUserClient userClient;
	private RedissonClient redissonClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		HttpServletRequest request = WebUtil.getRequest();

		String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		// 获取验证码
		String redisCode = String.valueOf(redissonClient.getBucket(CacheNames.CAPTCHA_KEY + key).get());
		// 判断验证码
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
		}

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
