package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;

import java.util.List;

@FunctionalInterface
public interface CarOutput {

    public boolean writeCars(List<CarEntity> entityList);

}
