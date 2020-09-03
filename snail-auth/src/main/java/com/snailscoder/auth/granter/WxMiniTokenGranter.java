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

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import com.snailscoder.core.log.exception.ServiceException;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.upms.entity.UserInfo;
import com.snailscoder.upms.entity.UserOauth;
import com.snailscoder.upms.feign.IUserClient;
import org.springframework.stereotype.Component;

/**
 * SocialTokenGranter
 *
 * @author snailscoder
 */
@AllArgsConstructor
@Component
public class WxMiniTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "wxmini";

	private final IUserClient userClient;
	private final WxMaService wxMaService;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		// 小程序授权码
		String code = tokenParameter.getAuthCode();
		try {
			WxMaJscode2SessionResult wxResult = wxMaService.getUserService().getSessionInfo(code);
			// 组装数据
			UserOauth userOauth = new UserOauth();
			userOauth.setSource(tokenParameter.getAuthSource());
			userOauth.setUuid(wxResult.getOpenid());
			userOauth.setUnionId(wxResult.getUnionid());
			userOauth.setAccessToken(wxResult.getSessionKey());
			// 远程调用，获取认证信息
			R<UserInfo> result = userClient.userAuthInfo(userOauth);
			return result.getData();
		} catch (WxErrorException e) {
			throw new ServiceException("wximin grant failure, auth response is not success");
		}
	}

}
