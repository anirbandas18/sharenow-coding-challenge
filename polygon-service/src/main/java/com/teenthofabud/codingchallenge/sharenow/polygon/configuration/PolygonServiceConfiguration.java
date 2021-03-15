package com.teenthofabud.codingchallenge.sharenow.polygon.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.teenthofabud.codingchallenge.sharenow.polygon.repository")
public class PolygonServiceConfiguration {
}
