package com.bobooi.mall.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author bobo
 */
@SpringBootApplication(scanBasePackages = "com.bobooi.mall")
@EnableJpaRepositories(basePackages = "com.bobooi.mall.data.repository.concrete")
@EnableElasticsearchRepositories(basePackages = "com.bobooi.mall.data.repository.concrete.search")
@EntityScan(basePackages="com.bobooi.mall.data.entity")
public class AppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApiApplication.class, args);
    }

}
