package com.teenthofabud.codingchallenge.sharenow.car.service;

import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarServiceException;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CarService {

    public List<CarVO> retrieveAllCars();

    public Set<CarDetailsVO> retrieveAllCarsWithDetails();

    public CarDetailsVO retrieveCarDetailsByVin(String vin) throws CarServiceException;

}
