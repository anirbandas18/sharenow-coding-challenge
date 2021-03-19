package com.teenthofabud.codingchallenge.sharenow.position.service.impl;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionErrorCode;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.service.PlacementService;
import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlacementServiceJTSImpl implements PlacementService {

    private GeometryFactory geometryFactory;

    @Autowired
    public PlacementServiceJTSImpl(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    @Override
    public boolean isCarInsidePolygon(CarDetailsDTO carDTO, StrategicPolygonDetailedDTO polygonDTO) throws PositionServiceException {
        if(carDTO == null) {
            throw new PositionServiceException("Invalid car", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"car is null"});
        } else if(polygonDTO == null) {
            throw new PositionServiceException("Invalid polygon", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"polygon is null"});
        } else {
            org.geojson.Polygon polygon = polygonDTO.getGeometry();
            if(polygon != null) {
                List<LngLatAlt> coordinates = polygon.getExteriorRing();
                if (coordinates != null && coordinates.size() < 3) {
                    throw new PositionServiceException("Invalid polygon", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"polygon vertex count < 3"});
                } else {
                    List<Coordinate> polygonPointsList = polygonDTO.getGeometry().getExteriorRing().stream()
                            .map(lla -> new Coordinate(lla.getLongitude(), lla.getLatitude())).collect(Collectors.toList());
                    polygonPointsList.add(polygonPointsList.get(0)); // sanitizing polygon coordinates as per GeoJSOn specifications
                    Coordinate[] polygonPointsArray = polygonPointsList.toArray(new Coordinate[polygonPointsList.size()]);
                    Polygon strategicPolygon = geometryFactory.createPolygon(polygonPointsArray);
                    Coordinate carCoordinates = new Coordinate(carDTO.getPosition().getLongitude(), carDTO.getPosition().getLatitude());
                    Point carLocation = geometryFactory.createPoint(carCoordinates);
                    return strategicPolygon.contains(carLocation) || strategicPolygon.getBoundary().contains(carLocation);
                }
            } else {
                throw new PositionServiceException("Invalid polygon", PositionErrorCode.INVALID_PARAMETER, new Object[] {"polygon coordinates"});
            }
        }
    }
}
