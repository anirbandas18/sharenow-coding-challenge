package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.impl;


import com.teenthofabud.codingchallenge.sharenow.polling.PollingMonitor;
import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.VehicleDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.PositionEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.VehicleEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter.PositionDTO2EntityConveter;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter.VehicleDTO2EntityConveter;
import com.teenthofabud.codingchallenge.sharenow.polling.filter.RestClientErrorHandler;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.VehicleInput;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.VehicleOutput;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.VehicleProcessor;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.VehicleRefreshService;
import com.teenthofabud.codingchallenge.sharenow.polling.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class Car2GoVehicleRefreshServiceImpl implements VehicleRefreshService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car2GoVehicleRefreshServiceImpl.class);

    private static String REQUEST_URL;

    private RestTemplate car2goClient;

    private PositionDTO2EntityConveter positionDto2Entity;

    private VehicleDTO2EntityConveter vehicleDto2Entity;

    private VehicleInput<String> input;

    private VehicleProcessor processor;

    private VehicleOutput output;

    private int counter;

    @Value("${ps.refresh.search.location:Stuttgart}")
    private String searchLocation;

    @Value("${ps.refresh.bucket.size:5}")
    private int bucketSize;

    @Value("${car2go.base.url:localhost:3000}")
    private String car2goBaseURL;

    @Value("${car2go.vehicles.by.location.uri:/vehicles/{locationName}}")
    private String car2goVehiclesByLocationURI;

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private RestClientErrorHandler errorHandler;

    @Autowired
    private PollingMonitor monitor;

    @PostConstruct
    private void init() {
        REQUEST_URL = String.join("", car2goBaseURL, car2goVehiclesByLocationURI);
        LOGGER.info("Fully qualified car2go vehicle search API: {}", REQUEST_URL);
        this.car2goClient = new RestTemplate();
        this.car2goClient.setErrorHandler(this.errorHandler);
        this.counter = 0;
        this.input = (sourceUrl) -> {
            List<VehicleDTO> dtoList = new ArrayList<>();
            String variableName = "locationName";
            Map<String, String> uriVariables = Collections.singletonMap(variableName, searchLocation);
            ResponseEntity<List<VehicleDTO>> responseEntity =
                    car2goClient.exchange(sourceUrl, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<VehicleDTO>>() {}, uriVariables);
            dtoList = responseEntity.getBody();
            LOGGER.info("Queried vehicle count: {}", dtoList.size());
            return dtoList;
        };
        this.positionDto2Entity = (dto) -> {
            PositionEntity entity = new PositionEntity();
            entity.setLatitude(dto.getLatitude());
            entity.setLongitude(dto.getLongitude());
            return entity;
        };
        this.vehicleDto2Entity = (dto) -> {
            VehicleEntity entity = new VehicleEntity();
            entity.setFuel(dto.getFuel());
            entity.setId(dto.getId());
            entity.setLocationId(dto.getLocationId());
            entity.setModel(dto.getModel());
            entity.setNumberPlate(dto.getNumberPlate());
            entity.setVin(dto.getVin());
            entity.setPosition(this.positionDto2Entity.convert(dto.getPosition()));
            entity.setUpdatedAt(new Date());
            return entity;
        };
        this.processor = (dtoList) -> {
            List<VehicleEntity> entityList = new ArrayList<>();
            for(VehicleDTO dto : dtoList) {
                VehicleEntity entity = this.vehicleDto2Entity.convert(dto);
                entityList.add(entity);
            }
            LOGGER.info("Processed vehicle count: {}", entityList.size());
            return entityList;
        };
        this.output = (entityList) -> {
            int count  = 0;
            for(VehicleEntity entity : entityList) {
                if(this.repository.save(entity) != null) {
                    count++;
                }
            }
            LOGGER.info("Updated vehicle count: {}", count);
            return count == entityList.size();
        };
    }


    @Override
    @Scheduled(cron = "${ps.refresh.cron.expression:*/5 * * * *}")
    public void collectLiveVehiclesForConfiguredCity() {
        synchronized (monitor) {
            List<VehicleDTO> vehicleDTOs = this.input.readVehicles(REQUEST_URL);
            List<VehicleEntity> vehicleEntities = this.processor.processVehicles(vehicleDTOs);
            boolean status  = this.output.writeVehicles(vehicleEntities);
            LOGGER.info("Vehicle refresh status: {}", status);
        }
    }

}
