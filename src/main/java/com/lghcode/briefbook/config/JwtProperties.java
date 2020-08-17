package com.lghcode.briefbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.lghcode.briefbook.constant.Constant.TOKEN_NAME;

/**
 * jwt相关配置
 * @Author lgh
 * @Date 2020/8/17 11:54
 */
@Configuration
@ConfigurationProperties(prefix = JwtProperties.JWT_PREFIX)
@Data
public class JwtProperties {

    public static final String JWT_PREFIX = "jwt";

    /**
     * 请求头参数名
     */
    private String header = TOKEN_NAME;

    /**
     * 密钥
     */
    private String secret = "defaultSecret";

    /**
     *过期时间
     */
    private Long expiration = 2592000L;

    /**
     * MD5加密key
     */
    private String md5Key = "randomKey";
}
