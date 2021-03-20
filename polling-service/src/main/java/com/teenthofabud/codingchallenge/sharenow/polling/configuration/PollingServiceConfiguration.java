package com.teenthofabud.codingchallenge.sharenow.polling.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class PollingServiceConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") int redisPort) {
        System.out.println(redisHost + ":" + redisPort);
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

}
