package com.teenthofabud.codingchallenge.sharenow.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServiceApplication {

    private static String FP;

    @Value("${spring.cloud.config.server.native.search-locations}")
    public void setFP(String filePath) {
        FP = filePath;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServiceApplication.class, args);
        System.out.println(FP);
    }

}
