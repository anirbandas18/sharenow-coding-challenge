package com.teenthofabud.codingchallenge.sharenow.position.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Duration;
import java.util.Locale;

@Component
@EnableFeignClients(basePackages = "com.teenthofabud.codingchallenge.sharenow.position.repository")
@EnableEurekaClient
public class PositionServiceConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }


    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCircuitBreakerFactory(
            @Value("${poss.circuit-breaker.failure.threshold-percentage}") float failureThresholdPercentage,
            @Value("${poss.circuit-breaker.wait.duration.in-open-state}") long waitDurationInOpenStateInMillis,
            @Value("${poss.circuit-breaker.sliding-window.size}") int slidingWindowSize,
            @Value("${poss.circuit-breaker.timeout.duration}") long timeoutDurationInSeconds) {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureThresholdPercentage)
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenStateInMillis))
                .slidingWindowSize(slidingWindowSize)
                .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(timeoutDurationInSeconds))
                .build();
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

}
