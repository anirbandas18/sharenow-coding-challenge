package com.teenthofabud.codingchallenge.sharenow.polling.refresh.service;

import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.CarDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;

import java.util.List;

@FunctionalInterface
public interface CarProcessor {

    public List<CarEntity> processCars(List<CarDTO> dtoList);

}
