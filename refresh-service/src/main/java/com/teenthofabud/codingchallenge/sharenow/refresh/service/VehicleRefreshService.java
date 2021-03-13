package com.teenthofabud.codingchallenge.sharenow.refresh.service;

import org.springframework.stereotype.Service;

@Service
public interface VehicleRefreshService {

    public void pollLiveVehiclesForConfiguredCity();

}
