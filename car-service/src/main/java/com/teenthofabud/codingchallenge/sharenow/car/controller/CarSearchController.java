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
import java.util.Set;

@RestController
@RequestMapping("search")
public class CarSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchController.class);

    @Autowired
    private CarService service;

    @GetMapping("vin/{vin}")
    public ResponseEntity<?> getCarByVin(@PathVariable String vin) throws CarServiceException {
        LOGGER.info("Requesting car with vin: {}", vin);
        CarDetailsVO vo = this.service.retrieveCarDetailsByVin(vin);
        ResponseEntity<CarDetailsVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with car of vin: {}", vin);
        return response;
    }

    @GetMapping
    public ResponseEntity<?> getAllCars() throws CarServiceException {
        LOGGER.info("Requesting all cars");
        List<CarVO> voList = this.service.retrieveAllCars();
        ResponseEntity<List<CarVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available cars");
        return response;
    }

    @GetMapping("withdetails")
    public ResponseEntity<?> getAllCarsWithDetails() throws CarServiceException {
        LOGGER.info("Requesting all cars and their details");
        Set<CarDetailsVO> voList = this.service.retrieveAllCarsWithDetails();
        ResponseEntity<Set<CarDetailsVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available cars and their details");
        return response;
    }

}
