package com.teenthofabud.codingchallenge.sharenow.polling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@EnableRedisRepositories
public class PollingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollingServiceApplication.class, args);
    }

}
