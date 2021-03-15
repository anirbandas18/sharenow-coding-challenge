package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.impl;

import com.teenthofabud.codingchallenge.sharenow.polling.PollingMonitor;
import com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.CarCleanupService;
import com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.CarStaleness;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.repository.CarRepository;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@Component
public class Car2GoCarCleanupServiceImpl implements CarCleanupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car2GoCarCleanupServiceImpl.class);

    @Value("${ps.cleanup.staleness.interval:60}")
    private int stalenessIntervalInSeconds;

    @Value("${ps.cleanup.ttl.eviction:1}")
    private long ttlEviction;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private CarRepository repository;

    @Autowired
    private PollingMonitor monitor;

    private CarStaleness staleDetector;

    @PostConstruct
    private void init() {
        this.staleDetector = (ve) -> {
            boolean status = false;
            if(ve != null) {
                long now = System.currentTimeMillis();
                long lastSeen = ve.getUpdatedAt().getTime();
                long elapsed = now - lastSeen;
                status = (elapsed/1000l) > stalenessIntervalInSeconds;
            }
            return status;
        };
    }


    @Override
    @Scheduled(cron = "${ps.cleanup.cron.expression:*/90 * * * *}")
    public void clearStaleCarsForConfiguredCity() {
        synchronized (monitor) {
            Iterable<CarEntity> itr = this.repository.findAll();
            RKeys keys = redisson.getKeys();
            int count = 0;
            LOGGER.info("Starting transaction for evicting stale cars by least recently updated policy");
            for(CarEntity entity : itr) {
                if(this.staleDetector.isCarStale(entity)) {
                    String carKey = entity.getCacheKey();
                    keys.expire(carKey, ttlEviction, TimeUnit.SECONDS);
                    LOGGER.info("Preparing cars with id {} for eviction by assigning the lowest ttl", entity.getId());
                    count++;
                }
            }
            LOGGER.info("Completed transaction for evicting {} stale cars", count);
        }
    }

}
