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

    /**
     * 节点域名
     */
    private String endpoint;

    /**
     * AccessKey
     */
    private String accessKeyId;

    /**
     * Accesssecret
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;

    /**
     * bucket下文件夹的路径
     */
    private String filePath;

    /**
     * 文件访问域名前缀域名
     */
    private String domain;
}


