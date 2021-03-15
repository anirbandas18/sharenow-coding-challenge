package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service;

import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;

@FunctionalInterface
public interface CarStaleness {

    public boolean isCarStale(CarEntity entity);

}
