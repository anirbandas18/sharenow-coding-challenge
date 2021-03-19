package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.configuration;

import io.lettuce.core.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class VehicleCleanupConfiguration {


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisClient lettuceClient(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") int redisPort) {
        String redisURL = String.format("redis://%s:%d", redisHost, redisPort);
        RedisClient lettuce = RedisClient.create(redisURL);
        return lettuce;
    }

}
