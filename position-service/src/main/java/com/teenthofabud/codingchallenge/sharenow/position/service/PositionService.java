package com.teenthofabud.codingchallenge.sharenow.position.service;

import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.Car2StrategicPolygonPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygon2CarPositioningVO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PositionService {

    public Car2StrategicPolygonPositioningVO retrievePositionOfCarAndItsEnclosingPolygonByVin(String vin) throws PositionServiceException;

    public StrategicPolygon2CarPositioningVO retrievePositionsOfAllCarsWithinPolygonByPolygonId(String polygonId) throws PositionServiceException;

    public Set<StrategicPolygon2CarPositioningVO> retrievePositionsOfAllCarsWithinPolygonByPolygonName(String name) throws PositionServiceException;

}
