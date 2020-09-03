package com.snailscoder.upms.dto;

import lombok.Data;

/**
 * @author: snailscoder
 * @date: 2020/8/25 下午5:13
 */
@Data
public class UserOauthEncryDTO {
	private Long id;
	private String iv;
	private String rawData;
	private String signature;
	private String encryptedData;
	private String cloudId;
}
