package com.teenthofabud.codingchallenge.sharenow.polygon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PolygonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolygonServiceApplication.class, args);
    }

}
