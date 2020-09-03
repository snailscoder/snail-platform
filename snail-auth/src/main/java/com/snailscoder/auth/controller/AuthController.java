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
package com.snailscoder.auth.controller;

import com.snailscoder.auth.granter.ITokenGranter;
import com.snailscoder.auth.granter.TokenGranterBuilder;
import com.snailscoder.auth.granter.TokenParameter;
import com.snailscoder.auth.utils.TokenUtil;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.secure.AuthInfo;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.support.Kv;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.WebUtil;
import com.snailscoder.upms.entity.UserInfo;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.snailscoder.common.cache.CacheNames.DICT_LIST;

/**
 * 认证模块
 *
 * @author snailscoder
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private RedissonClient redissonClient;

	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthInfo> token(@RequestBody TokenParameter tokenParameter) {
		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);
		tokenParameter.setUserType(userType);
		ITokenGranter granter = TokenGranterBuilder.getGranter(tokenParameter.getGrantType());
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || (userInfo.getUser().getId() == null && !Func.equals("microtrade",tokenParameter.getAuthSource()))) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}

	@GetMapping("/captcha")
	@ApiOperation(value = "获取验证码")
	public R<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		redissonClient.getBucket(CacheNames.CAPTCHA_KEY + key).set(verCode,30L,TimeUnit.MINUTES);
		// 将key和base64返回给前端
		return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
	}

}
