package com.teenthofabud.codingchallenge.sharenow.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServiceApplication {

    @Value("${spring.cloud.config.server.native.search-locations}")
    public void setBaseLocation(String baseLocation) {
        BL = baseLocation;
    }
    static String BL;

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServiceApplication.class, args);
        System.out.println(BL);
    }

}
