package com.lghcode.briefbook.config;

import com.lghcode.briefbook.interceptor.AuthInterceptor;
import com.lghcode.briefbook.util.DateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc相关配置
 * @author lgh
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

    /**
     * 拦截器配置
     * @param registry 实例
     */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/oss/**")
				.excludePathPatterns("/user/loginSms")
				.excludePathPatterns("/user/loginPw")
				.excludePathPatterns("/user/sendSms");
	}

    /**
     *跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("*");
    }

	/**
	 * mvc类型转换,数据格式化
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new DateConverter());
	}
}
