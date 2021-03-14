package com.teenthofabud.codingchallenge.sharenow.vehicle.service;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleServiceException;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    public List<VehicleVO> retrieveAllVehicles();

    public VehicleDetailsVO retrieveVehicleDetailsByVin(String vin) throws VehicleServiceException;

}
