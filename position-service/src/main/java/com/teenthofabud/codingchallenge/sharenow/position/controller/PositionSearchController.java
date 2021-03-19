package com.teenthofabud.codingchallenge.sharenow.position.controller;

import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.Car2StrategicPolygonPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygon2CarPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class PositionSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionSearchController.class);

    @Autowired
    private PositionService service;

    @GetMapping("car/vin/{vin}")
    public ResponseEntity<?> getVehicleAndItsEnclosingStrategicPolygon(@PathVariable String vin) throws PositionServiceException {
        LOGGER.info("Determining polygon enclosing the car with vin");
        Car2StrategicPolygonPositioningVO vo = this.service.retrievePositionOfCarAndItsEnclosingPolygonByVin(vin);
        ResponseEntity<Car2StrategicPolygonPositioningVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Determined enclosed polygon for the car with vin");
        return response;
    }

    @GetMapping("polygon/id/{id}")
    public ResponseEntity<?> getStrategicPolygonAndTheVehiclesItContains(@PathVariable String id) throws PositionServiceException {
        LOGGER.info("Determining all cars enclosed within the polygon by id");
        StrategicPolygon2CarPositioningVO vo = this.service.retrievePositionsOfAllCarsWithinPolygonByPolygonId(id);
        ResponseEntity<StrategicPolygon2CarPositioningVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Determined all cars enclosed within this polygon by its id");
        return response;
    }

}
