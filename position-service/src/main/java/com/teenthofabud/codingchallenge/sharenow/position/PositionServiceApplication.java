package com.teenthofabud.codingchallenge.sharenow.position;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class PositionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositionServiceApplication.class, args);
    }

}
