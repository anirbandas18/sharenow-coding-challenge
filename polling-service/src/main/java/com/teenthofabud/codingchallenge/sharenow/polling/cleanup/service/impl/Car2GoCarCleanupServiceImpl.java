package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.impl;

import com.teenthofabud.codingchallenge.sharenow.polling.PollingMonitor;
import com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.CarCleanupService;
import com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.CarStaleness;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.repository.CarRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class Car2GoCarCleanupServiceImpl implements CarCleanupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car2GoCarCleanupServiceImpl.class);

    @Value("${ps.cleanup.staleness.interval}")
    private int stalenessIntervalInSeconds;

    @Value("${ps.cleanup.ttl.eviction}")
    private long ttlEviction;

    @Autowired
    private RedisClient lettuce;

    @Autowired
    private CarRepository repository;

    @Autowired
    private PollingMonitor monitor;

    private CarStaleness staleDetector;

    private StatefulRedisConnection<String, String> connection;

    @PostConstruct
    private void init() {
        this.staleDetector = (ve) -> {
            boolean status = false;
            if(ve != null) {
                long now = System.currentTimeMillis();
                long lastSeen = ve.getUpdatedAt().getTime();
                long elapsed = now - lastSeen;
                status = (elapsed/1000l) >= stalenessIntervalInSeconds;
            }
            return status;
        };
    }

    @PreDestroy
    private void destroy() {
        if(this.connection != null) {
            this.connection.close();
        }
        if(this.lettuce != null) {
            this.lettuce.shutdown();
        }
    }

    @Override
    @Scheduled(cron = "${ps.cleanup.cron.expression:*/90 * * * * *}")
    public void clearStaleCarsForConfiguredCity() {
        synchronized (monitor) {
            Iterable<CarEntity> itr = this.repository.findAll();
            StatefulRedisConnection<String, String> connection = this.lettuce.connect();
            RedisCommands<String, String> entries =  connection.sync();
            int count = 0;
            LOGGER.info("Starting transaction for evicting stale cars by least recently updated policy");
            for(CarEntity entity : itr) {
                if(this.staleDetector.isCarStale(entity)) {
                    String carKey = entity.getCacheKey();
                    boolean status = entries.expire(carKey, ttlEviction);
                    LOGGER.info("Preparing cars with vin {} for eviction by assigning the lowest ttl: {}", entity.getVin(), status);
                    count++;
                }
            }
            LOGGER.info("Completed transaction for evicting {} stale cars", count);
        }
    }

}
