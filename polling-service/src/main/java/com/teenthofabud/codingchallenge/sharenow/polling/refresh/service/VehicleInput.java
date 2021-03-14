package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto.VehicleDTO;

import java.util.List;

@FunctionalInterface
public interface VehicleInput<T> {

    public List<VehicleDTO> readVehicles(T source);

}
