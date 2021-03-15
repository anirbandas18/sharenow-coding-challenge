package com.teenthofabud.codingchallenge.sharenow.car.service.impl;

import com.teenthofabud.codingchallenge.sharenow.car.converter.PositionEntity2VOConverter;
import com.teenthofabud.codingchallenge.sharenow.car.converter.CarEntity2DetailedVOConverter;
import com.teenthofabud.codingchallenge.sharenow.car.converter.CarEntity2VOConverter;
import com.teenthofabud.codingchallenge.sharenow.car.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarErrorCode;
import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarServiceException;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.PositionVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarVO;
import com.teenthofabud.codingchallenge.sharenow.car.repository.CarRepository;
import com.teenthofabud.codingchallenge.sharenow.car.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository repository;

    private PositionEntity2VOConverter positionConverter;

    private CarEntity2VOConverter simpleVOConverter;

    private CarEntity2DetailedVOConverter complexVOConverter;

    private Comparator<CarEntity> cmpVehicleByVin;

    @PostConstruct
    private void init() {
        this.positionConverter = (entity) -> {
            PositionVO vo = new PositionVO();
            if(entity != null) {
                vo.setLatitude(entity.getLatitude());
                vo.setLongitude(entity.getLongitude());
            }
            return vo;
        };
        this.simpleVOConverter = (entity) -> {
          CarVO vo = new CarVO();
          if(entity != null) {
              vo.setId(entity.getId());
              vo.setNumberPlate(entity.getNumberPlate());
              vo.setVin(entity.getVin());
              vo.setLocationId(entity.getLocationId());
          }
          return vo;
        };
        this.complexVOConverter = (entity) -> {
            CarDetailsVO vo = new CarDetailsVO();
            if(entity != null) {
                vo.setId(entity.getId());
                vo.setNumberPlate(entity.getNumberPlate());
                vo.setVin(entity.getVin());
                vo.setFuel(entity.getFuel());
                vo.setLocationId(entity.getLocationId());
                vo.setModel(entity.getModel());
                vo.setPosition(positionConverter.convert(entity.getPosition()));
            }
            return vo;
        };
        this.cmpVehicleByVin = (v1, v2) -> {
          return v1.getVin().compareTo(v2.getVin());
        };
    }

    @Override
    public List<CarVO> retrieveAllVehicles() {
        List<CarVO> carVOList = new ArrayList<>();
        Iterable<CarEntity> entityItr = this.repository.findAll();
        for(CarEntity entity : entityItr) {
            CarVO vo = this.simpleVOConverter.convert(entity);
            carVOList.add(vo);
        }
        LOGGER.info("Found {} vehicles", carVOList.size());
        return carVOList;
    }

    @Override
    public CarDetailsVO retrieveVehicleDetailsByVin(String vin) throws CarServiceException {
        if(StringUtils.hasText(vin)) {
            Iterable<CarEntity> entityItr = this.repository.findAll();
            List<CarEntity> entityList = new ArrayList<>();
            for(CarEntity entity : entityItr) {
                entityList.add(entity);
            }
            Collections.sort(entityList, cmpVehicleByVin);
            int idx = Collections.binarySearch(entityList, new CarEntity(vin), cmpVehicleByVin);
            if(idx >= 0) {
                CarEntity entity = entityList.get(idx);
                CarDetailsVO vo = this.complexVOConverter.convert(entity);
                LOGGER.info("Found vehicle by vin: {}", vin);
                return vo;
            } else {
                LOGGER.error("No vehicle found with vin: {}", vin);
                throw new CarServiceException("No vehicle found that matches with vin", CarErrorCode.NOT_FOUND, new Object[] {"vin", vin});
            }
        } else {
            LOGGER.error("Invalid vin: {}", vin);
            throw new CarServiceException("Invalid vin", CarErrorCode.INVALID_PARAMETER, new Object[] {"vin"});
        }
    }
}
