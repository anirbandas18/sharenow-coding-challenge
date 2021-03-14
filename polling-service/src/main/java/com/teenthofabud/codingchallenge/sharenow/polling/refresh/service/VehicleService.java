package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto.VehicleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    public List<VehicleDTO> retrieveAllVehicles();

    public VehicleDTO retrieveVehicleDetailsByVIN(String vin);

}
