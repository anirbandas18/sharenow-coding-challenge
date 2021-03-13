package com.teenthofabud.codingchallenge.sharenow.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.refresh.model.dto.VehicleDTO;
import com.teenthofabud.codingchallenge.sharenow.refresh.model.entity.VehicleEntity;

import java.util.List;

@FunctionalInterface
public interface VehicleProcessor {

    public List<VehicleEntity> processVehicles(List<VehicleDTO> dtoList);

}
