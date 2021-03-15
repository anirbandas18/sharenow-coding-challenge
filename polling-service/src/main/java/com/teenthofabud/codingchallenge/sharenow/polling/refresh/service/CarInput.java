package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;


import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.CarDTO;

import java.util.List;

@FunctionalInterface
public interface CarInput<T> {

    public List<CarDTO> readCars(T source);

}
