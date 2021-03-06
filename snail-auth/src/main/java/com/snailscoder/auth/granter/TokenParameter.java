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

import lombok.Data;
import com.snailscoder.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author snailscoder
 */
@Data
public class TokenParameter {
	private String grantType = "password";
	private String refreshToken;
	private String userType;
	private String account;
	private String password;
	private String authCode;
	private String authSource;
	private String authState;
}
