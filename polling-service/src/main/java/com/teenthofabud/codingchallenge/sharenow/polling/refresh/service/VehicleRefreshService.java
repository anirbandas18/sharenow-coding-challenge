package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;

import org.springframework.stereotype.Service;

@Service
public interface VehicleRefreshService {

    public void collectLiveVehiclesForConfiguredCity();

}
