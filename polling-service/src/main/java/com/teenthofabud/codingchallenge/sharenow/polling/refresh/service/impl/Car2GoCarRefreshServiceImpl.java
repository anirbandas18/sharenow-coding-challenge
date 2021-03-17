package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.impl;


import com.teenthofabud.codingchallenge.sharenow.polling.PollingMonitor;
import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.CarDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.PositionEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter.PositionDTO2EntityConverter;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter.CarDTO2EntityConverter;
import com.teenthofabud.codingchallenge.sharenow.polling.filter.RestClientErrorHandler;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.CarInput;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.CarOutput;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.CarProcessor;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.service.CarRefreshService;
import com.teenthofabud.codingchallenge.sharenow.polling.repository.CarRepository;
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
public class Car2GoCarRefreshServiceImpl implements CarRefreshService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car2GoCarRefreshServiceImpl.class);

    private static String REQUEST_URL;

    private RestTemplate car2goClient;

    private PositionDTO2EntityConverter positionDto2Entity;

    private CarDTO2EntityConverter vehicleDto2Entity;

    private CarInput<String> input;

    private CarProcessor processor;

    private CarOutput output;

    private int counter;

    @Value("${ps.refresh.search.location}")
    private String searchLocation;

    @Value("${car2go.base.url:localhost}")
    private String car2goBaseURL;

    @Value("${car2go.vehicles.by.location.uri}")
    private String car2goVehiclesByLocationURI;

    @Autowired
    private CarRepository repository;

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
            List<CarDTO> dtoList = new ArrayList<>();
            String variableName = "locationName";
            Map<String, String> uriVariables = Collections.singletonMap(variableName, searchLocation);
            ResponseEntity<List<CarDTO>> responseEntity =
                    car2goClient.exchange(sourceUrl, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<CarDTO>>() {}, uriVariables);
            dtoList = responseEntity.getBody();
            LOGGER.info("Queried car count: {}", dtoList.size());
            return dtoList;
        };
        this.positionDto2Entity = (dto) -> {
            PositionEntity entity = new PositionEntity();
            entity.setLatitude(dto.getLatitude());
            entity.setLongitude(dto.getLongitude());
            return entity;
        };
        this.vehicleDto2Entity = (dto) -> {
            CarEntity entity = new CarEntity();
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
            List<CarEntity> entityList = new ArrayList<>();
            for(CarDTO dto : dtoList) {
                CarEntity entity = this.vehicleDto2Entity.convert(dto);
                entityList.add(entity);
            }
            LOGGER.info("Processed car count: {}", entityList.size());
            return entityList;
        };
        this.output = (entityList) -> {
            int count  = 0;
            for(CarEntity entity : entityList) {
                if(this.repository.save(entity) != null) {
                    count++;
                }
            }
            LOGGER.info("Updated car count: {}", count);
            return count == entityList.size();
        };
    }


    @Override
    @Scheduled(cron = "${ps.refresh.cron.expression:*/5 * * * *}")
    public void collectLiveCarsForConfiguredCity() {
        synchronized (monitor) {
            List<CarDTO> dtoList = this.input.readCars(REQUEST_URL);
            List<CarEntity> entityList = this.processor.processCars(dtoList);
            boolean status  = this.output.writeCars(entityList);
            LOGGER.info("car refresh status: {}", status);
        }
    }

}
