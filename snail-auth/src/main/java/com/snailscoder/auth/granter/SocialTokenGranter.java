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

import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.entity.UserOauth;
import com.snailscoder.upms.feign.IUserClient;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import com.snailscoder.auth.utils.TokenUtil;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.social.props.SocialProperties;
import com.snailscoder.core.social.utils.SocialUtil;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.BeanUtil;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.WebUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * SocialTokenGranter
 *
 * @author snailscoder
 */
@Component
@AllArgsConstructor
public class SocialTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "social";

	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private final IUserClient userClient;
	private final SocialProperties socialProperties;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		// 开放平台来源
		String sourceParameter = tokenParameter.getAuthSource();
		// 匹配是否有别名定义
		String source = socialProperties.getAlias().getOrDefault(sourceParameter, sourceParameter);
		// 开放平台授权码
		String code = tokenParameter.getAuthCode();
		// 开放平台状态吗
		String state = tokenParameter.getAuthState();

		// 获取开放平台授权数据
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		AuthCallback authCallback = new AuthCallback();
		authCallback.setCode(code);
		authCallback.setState(state);
		AuthResponse authResponse = authRequest.login(authCallback);
		AuthUser authUser;
		if (authResponse.getCode() == AUTH_SUCCESS_CODE) {
			authUser = (AuthUser) authResponse.getData();
		} else {
			throw new ServiceException("social grant failure, auth response is not success");
		}

		// 组装数据
		UserOauth userOauth = Objects.requireNonNull(BeanUtil.copy(authUser, UserOauth.class));
		userOauth.setSource(authUser.getSource());
		userOauth.setUuid(authUser.getUuid());
		userOauth.setUnionId(authUser.getToken().getUnionId());
		// 远程调用，获取认证信息
		R<UserInfo> result = userClient.userAuthInfo(userOauth);
		return result.getData();
	}

}
