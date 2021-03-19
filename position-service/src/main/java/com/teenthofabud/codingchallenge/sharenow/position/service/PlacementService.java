package com.teenthofabud.codingchallenge.sharenow.position.service;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import org.springframework.stereotype.Service;

@Service
public interface PlacementService {

    public boolean isCarInsidePolygon(CarDetailsDTO carDTO, StrategicPolygonDetailedDTO polygonDTO) throws PositionServiceException;

}
