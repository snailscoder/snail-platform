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

package com.snailscoder.upms.dto;

import lombok.Data;

/**
 * @author: snailscoder
 * @date: 2020/9/11 上午10:39
 */
@Data
public class CredentialDTO {
	private Credentials credentials;
	private String requestId;
	private String expiration;
	private Long startTime;
	private Long expiredTime;

	@Data
	public static class Credentials{
		private String tmpSecretId;
		private String tmpSecretKey;
		private String sessionToken;
	}
}
