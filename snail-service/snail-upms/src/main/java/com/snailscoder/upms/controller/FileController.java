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

package com.snailscoder.upms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.upms.dto.UploadSignDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: snailscoder
 * @date: 2020/9/9 下午6:09
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "文件", tags = "文件")
public class FileController {
	private final COSClient cosClient;
	private final COSCredentials cosCredentials;

	@GetMapping("/presigned")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "预签名 URL", notes = "生成腾讯云文件预签名 URL")
	public R<UploadSignDTO> getPresigned(String fileName){
		UploadSignDTO signDTO = new UploadSignDTO();
		//重新生成文件Key，由年月日作为文件夹，文件名采用雪花算法生成
		String mimeType = FileUtil.getSuffix(fileName);
		String newFileName = IdUtil.getSnowflake(1,1).nextIdStr() + "." + mimeType;
		String key = DateUtil.format(new Date(),"/yyyy/MM/dd/");
		key = key + newFileName;
		signDTO.setFileKey(key);
		COSSigner signer = cosClient.getClientConfig().getCosSigner();
		//根据临时密钥计算签名信息
		//这里设置签名在半个小时后过期
		Date expiredTime = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
		// 要签名的 key, 生成的签名只能用于对应此 key 的上传
		String sign = signer.buildAuthorizationStr(HttpMethodName.POST, "/", cosCredentials, expiredTime);
		signDTO.setSignature(sign);
		return R.data(signDTO);
	}

}
