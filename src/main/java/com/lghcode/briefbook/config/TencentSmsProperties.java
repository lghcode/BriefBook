package com.lghcode.briefbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author lgh
 * @Date 2020/8/10 17:41
 */
@Component
@PropertySource(value = "classpath:application.yml",encoding = "utf-8")
@ConfigurationProperties(prefix = "tencent.sms")
@Data
public class TencentSmsProperties {

    /**
     *腾讯云账户密钥对id
     */
    private String secretId;

    /**
     *腾讯云账户密钥对key
     */
    private String secretKey;

    /**
     * 应用appId
     */
    private String sdkAppId;

    /**
     * 短信签名内容
     */
    private String sign;

    /**
     * 短信模板id
     */
    private String templateId;
}


