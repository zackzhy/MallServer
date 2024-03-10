package com.bobooi.mall.data.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobo
 * @date 2021/8/8
 */

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue");
    }

}