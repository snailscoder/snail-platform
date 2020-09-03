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
import com.snailscoder.upms.service.IUserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 用户服务Feign实现类
 *
 * @author snailscoder
 */
@RestController
@AllArgsConstructor
@Api(value = "用户Client", tags = "用户Client")
public class UserClient implements IUserClient {

	IUserService userService;

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.data(userService.userInfo(userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info")
	public R<UserInfo> userInfo(String account, String password) {
		return R.data(userService.userInfo(account, password));
	}

	@Override
	@PostMapping(API_PREFIX + "/user-auth-info")
	public R<UserInfo> userAuthInfo(UserOauth userOauth) {
		return R.data(userService.userInfo(userOauth));
	}
}
