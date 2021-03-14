package com.teenthofabud.codingchallenge.sharenow.polling.refresh.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class VehicleRefreshConfiguration {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}