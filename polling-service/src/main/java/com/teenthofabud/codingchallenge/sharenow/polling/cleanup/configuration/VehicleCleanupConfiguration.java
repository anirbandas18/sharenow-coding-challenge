package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.configuration;

import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleCleanupConfiguration {

    @Bean
    public RedisClient lettuceClient(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") int redisPort) {
        String redisURL = String.format("redis://%s:%d", redisHost, redisPort);
        System.out.println(redisURL);
        RedisClient lettuce = RedisClient.create(redisURL);
        return lettuce;
    }

}
