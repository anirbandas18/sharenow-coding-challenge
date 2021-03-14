package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service;

import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.VehicleEntity;

@FunctionalInterface
public interface VehicleStaleness {

    public boolean isVehicleStale(VehicleEntity entity);

}
