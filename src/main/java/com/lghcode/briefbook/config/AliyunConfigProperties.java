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
@ConfigurationProperties(prefix = "oss")
@Data
public class AliyunConfigProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String filePath;

    private String domain;
}


