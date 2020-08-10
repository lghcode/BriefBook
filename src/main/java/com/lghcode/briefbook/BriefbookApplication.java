package com.lghcode.briefbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author lgh
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.lghcode.briefbook.mapper")
public class BriefbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriefbookApplication.class, args);
    }

}
