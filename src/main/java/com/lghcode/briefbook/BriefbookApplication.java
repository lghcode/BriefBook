package com.lghcode.briefbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lgh
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.lghcode.briefbook.mapper")
@EnableTransactionManagement
public class BriefbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriefbookApplication.class, args);
    }

}
