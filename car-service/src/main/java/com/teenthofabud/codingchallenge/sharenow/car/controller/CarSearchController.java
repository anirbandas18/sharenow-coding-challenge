package com.teenthofabud.codingchallenge.sharenow.car.controller;

import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarServiceException;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarVO;
import com.teenthofabud.codingchallenge.sharenow.car.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class CarSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchController.class);

    @Autowired
    private CarService service;

    @GetMapping("vin/{vin}")
    public ResponseEntity<?> getVehicleByVin(@PathVariable String vin) throws CarServiceException {
        LOGGER.info("Requesting vehicle with vin: {}", vin);
        CarDetailsVO vo = this.service.retrieveVehicleDetailsByVin(vin);
        ResponseEntity<CarDetailsVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with vehicle of vin: {}", vin);
        return response;
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles() throws CarServiceException {
        LOGGER.info("Requesting all vehicles");
        List<CarVO> voList = this.service.retrieveAllVehicles();
        ResponseEntity<List<CarVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available vehicles");
        return response;
    }

}
