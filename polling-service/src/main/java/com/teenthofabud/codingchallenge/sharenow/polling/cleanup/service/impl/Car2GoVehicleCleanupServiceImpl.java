package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.impl;

import com.teenthofabud.codingchallenge.sharenow.polling.PollingMonitor;
import com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service.VehicleCleanupService;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.entity.VehicleEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.repository.VehicleRepository;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class Car2GoVehicleCleanupServiceImpl implements VehicleCleanupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car2GoVehicleCleanupServiceImpl.class);

    private static final long MIN_TTL_FOR_EVICTION = 1l;

    @Value("${ps.cleanup.staleness.interval:60}")
    private int stalenessIntervalInSeconds;

    @Value("${ps.cleanup.stale.ttl:1}")
    private long ttlSeconds;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private PollingMonitor monitor;


    @Override
    @Scheduled(cron = "${ps.cleanup.cron.expression:*/90 * * * *}")
    public void clearStaleVehiclesForConfiguredCity() {
        synchronized (monitor) {
            Iterable<VehicleEntity> itr = this.repository.findAll();
            RKeys keys = redisson.getKeys();
            int count = 0;
            LOGGER.info("Starting transaction for evicting stale vehicles by least recently updated policy");
            for(VehicleEntity entity : itr) {
                if(isVehicleStale(entity)) {
                    String vehicleId = String.valueOf(entity.getId());
                    keys.expire(vehicleId, MIN_TTL_FOR_EVICTION, TimeUnit.MILLISECONDS);
                    LOGGER.info("Preparing vehicle with id {} for eviction by assigning the lowest ttl", vehicleId);
                    count++;
                }
            }
            LOGGER.info("Completed transaction for evicting {} stale vehicles", count);
        }
    }

    private boolean isVehicleStale(VehicleEntity ve) {
        boolean status = false;
        long now = System.currentTimeMillis();
        long lastSeen = ve.getUpdatedAt().getTime();
        long elapsed = now - lastSeen;
        status = (elapsed/1000l) > stalenessIntervalInSeconds;
        return status;
    }

}
