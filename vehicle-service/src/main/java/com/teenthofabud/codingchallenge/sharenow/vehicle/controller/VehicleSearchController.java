package com.teenthofabud.codingchallenge.sharenow.vehicle.controller;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleErrorCode;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleServiceException;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService service;

    @GetMapping("vin/{vin}")
    public ResponseEntity<?> getVehicleByVin(@PathVariable String vin) throws VehicleServiceException{
        if(StringUtils.hasText(vin)) {
            VehicleDetailsVO vo = this.service.retrieveVehicleDetailsByVin(vin);
            ResponseEntity<VehicleDetailsVO> response = ResponseEntity.ok(vo);
            return response;
        } else {
            throw new VehicleServiceException("", VehicleErrorCode.INVALID_PARAMETER, new Object[] {"vin"});
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles() throws VehicleServiceException{
        List<VehicleVO> voList = this.service.retrieveAllVehicles();
        ResponseEntity<List<VehicleVO>> response = ResponseEntity.ok(voList);
        return response;
    }

}
