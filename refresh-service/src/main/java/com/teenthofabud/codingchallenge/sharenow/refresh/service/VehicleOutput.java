package com.teenthofabud.codingchallenge.sharenow.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.refresh.model.entity.VehicleEntity;

import java.util.List;

@FunctionalInterface
public interface VehicleOutput {

    public boolean writeVehicles(List<VehicleEntity> entityList);

}
