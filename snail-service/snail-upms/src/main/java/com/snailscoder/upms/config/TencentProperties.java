package com.snailscoder.upms.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huaxin
 * @version 1.0
 * @date 2019-05-24 15:03
 */
@Data
@ConfigurationProperties(prefix = "snail.tenant.cos")
public class TencentProperties {
    private String secretId = "AKID3ykMnBceJo8LNGoWxFMICxZ0ISSGGTzM";
    private String secretKey = "LVZVHrKckV8ZobI422qTzLVTL2GSUOBn";
    private String reginName = "ap-beijing";
    private String bucketName = "wxma-1254014761";
}
