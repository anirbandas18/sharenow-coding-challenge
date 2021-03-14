package com.teenthofabud.codingchallenge.sharenow.vehicle.service.impl;

import com.teenthofabud.codingchallenge.sharenow.vehicle.converter.PositionEntity2VOConverter;
import com.teenthofabud.codingchallenge.sharenow.vehicle.converter.VehicleEntity2DetailedVOConverter;
import com.teenthofabud.codingchallenge.sharenow.vehicle.converter.VehicleEntity2VOConverter;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity.VehicleEntity;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleErrorCode;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleServiceException;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.PositionVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.repository.VehicleRepository;
import com.teenthofabud.codingchallenge.sharenow.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class VehicleServiceImpl implements VehicleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleRepository repository;

    private PositionEntity2VOConverter positionConverter;

    private VehicleEntity2VOConverter simpleVOConverter;

    private VehicleEntity2DetailedVOConverter complexVOConverter;

    private Comparator<VehicleEntity> cmpVehicleByVin;

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
          VehicleVO vo = new VehicleVO();
          if(entity != null) {
              vo.setId(entity.getId());
              vo.setNumberPlate(entity.getNumberPlate());
              vo.setVin(entity.getVin());
              vo.setLocationId(entity.getLocationId());
          }
          return vo;
        };
        this.complexVOConverter = (entity) -> {
            VehicleDetailsVO vo = new VehicleDetailsVO();
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
    public List<VehicleVO> retrieveAllVehicles() {
        List<VehicleVO> vehicleVOList = new ArrayList<>();
        Iterable<VehicleEntity> entityItr = this.repository.findAll();
        for(VehicleEntity entity : entityItr) {
            VehicleVO vo = this.simpleVOConverter.convert(entity);
            vehicleVOList.add(vo);
        }
        return vehicleVOList;
    }

    @Override
    public VehicleDetailsVO retrieveVehicleDetailsByVin(String vin) throws VehicleServiceException {
        if(StringUtils.hasText(vin)) {
            Iterable<VehicleEntity> entityItr = this.repository.findAll();
            List<VehicleEntity> entityList = new ArrayList<>();
            for(VehicleEntity entity : entityItr) {
                entityList.add(entity);
            }
            Collections.sort(entityList, cmpVehicleByVin);
            int idx = Collections.binarySearch(entityList, new VehicleEntity(vin), cmpVehicleByVin);
            //List<VehicleEntity> entityList = this.repository.findByVin(vin);
            //if(entityList != null && entityList.size() == 1)
            if(idx >= 0) {
                VehicleEntity entity = entityList.get(idx);
                VehicleDetailsVO vo = this.complexVOConverter.convert(entity);
                return vo;
            } else {
                throw new VehicleServiceException("", VehicleErrorCode.NOT_FOUND, new Object[] {"vin", vin});
            }
        } else {
            throw new VehicleServiceException("", VehicleErrorCode.INVALID_PARAMETER, new Object[] {"vin"});
        }
    }
}
