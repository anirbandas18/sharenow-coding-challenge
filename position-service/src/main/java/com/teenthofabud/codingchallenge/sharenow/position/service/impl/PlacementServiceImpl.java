package com.teenthofabud.codingchallenge.sharenow.position.service.impl;

import com.teenthofabud.codingchallenge.sharenow.position.model.Orientation;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionErrorCode;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.service.PlacementService;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlacementServiceImpl implements PlacementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlacementServiceImpl.class);

    @Value("${poss.placement.infinite.end.limit}")
    private int infiniteEnd;

    private boolean checkCollinearity(Point a, Point b, Point c) {
        LngLatAlt aCoordinates = a.getCoordinates();
        LngLatAlt bCoordinates = b.getCoordinates();
        LngLatAlt cCoordinates = c.getCoordinates();
        if (bCoordinates.getLongitude() <= Math.max(aCoordinates.getLongitude(), cCoordinates.getLongitude())
                && bCoordinates.getLongitude() >= Math.min(aCoordinates.getLongitude(), cCoordinates.getLongitude())
                && bCoordinates.getLatitude() <= Math.max(aCoordinates.getLatitude(), cCoordinates.getLatitude())
                && bCoordinates.getLatitude() >= Math.min(aCoordinates.getLatitude(), cCoordinates.getLatitude())) {
            return true;
        } else {
            return false;
        }
    }

    private Orientation determineOrientation(Point a, Point b, Point c) {
        LngLatAlt aCoordinates = a.getCoordinates();
        LngLatAlt bCoordinates = b.getCoordinates();
        LngLatAlt cCoordinates = c.getCoordinates();
        double slopeDifference = (bCoordinates.getLatitude() - aCoordinates.getLatitude()) * (cCoordinates.getLongitude() - bCoordinates.getLongitude())
                - (bCoordinates.getLongitude() - aCoordinates.getLongitude()) * (cCoordinates.getLatitude() - bCoordinates.getLatitude());
        Orientation orientation = Orientation.COLLINEAR;
        if ((int)slopeDifference == 1) {
            orientation = Orientation.CLOCKWISE;
        } else if((int)slopeDifference == 2) {
            orientation = Orientation.ANTI_CLOCKWISE;
        }
        return orientation;
    }

    private boolean doesIntersect(Point a1, Point b1, Point a2, Point b2) {
        boolean status = false;
        Orientation orientation1 = this.determineOrientation(a1, b1, a2);
        Orientation orientation2 = this.determineOrientation(a1, b1, b2);
        Orientation orientation3 = this.determineOrientation(a2, b2, a1);
        Orientation orientation4 = this.determineOrientation(a2, b2, a2);
        if (orientation1 != orientation2 && orientation3 != orientation4) {
            status = true;
        }
        if (orientation1 == Orientation.COLLINEAR && this.checkCollinearity(a1, a2, b1)) {
            status = true;
        }
        if (orientation2 == Orientation.COLLINEAR && this.checkCollinearity(a1, b2, b1)) {
            status = true;
        }
        if (orientation3 == Orientation.COLLINEAR && this.checkCollinearity(a2, a1, b2)) {
            status = true;
        }
        if (orientation4 == Orientation.COLLINEAR && this.checkCollinearity(a2, b1, b2)) {
            status = true;
        }
        return status;
    }

    @Override
    public boolean isCarInsidePolygonConsiderOnlyOuterRing(CarDetailsDTO carDTO, StrategicPolygonDetailedDTO polygonDTO) throws PositionServiceException {
        if(carDTO == null) {
            throw new PositionServiceException("Invalid car", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"car is null"});
        } else if(polygonDTO == null) {
            throw new PositionServiceException("Invalid polygon", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"polygon is null"});
        } else {
            Polygon polygon = polygonDTO.getGeometry();
            if(polygon != null) {
                List<LngLatAlt> coordinates = polygon.getExteriorRing();
                if (coordinates != null && coordinates.size() < 3) {
                    throw new PositionServiceException("Invalid polygon", PositionErrorCode.UNEXPECTED_PARAMETER, new Object[] {"polygon vertex count < 3"});
                } else {
                    Point carCoordinates = new Point(carDTO.getPosition().getLongitude(), carDTO.getPosition().getLatitude());
                    Point extremeEnd = new Point(infiniteEnd, carCoordinates.getCoordinates().getLatitude());
                    int intersectionCount = 0, i = 0;
                    do {
                        int j = (i + 1) % coordinates.size();
                        Point a1 = new Point(coordinates.get(i));
                        Point b1 = new Point(coordinates.get(j));
                        if (this.doesIntersect(a1, b1, carCoordinates, extremeEnd)) {
                            if (this.determineOrientation(a1, carCoordinates, b1) == Orientation.COLLINEAR) {
                                return this.checkCollinearity(a1, carCoordinates, b1);
                            }
                            intersectionCount++;
                        }
                        i = j;
                    } while (i != 0);
                    return (intersectionCount % 2 == 1);
                }
            } else {
                throw new PositionServiceException("Invalid polygon", PositionErrorCode.INVALID_PARAMETER, new Object[] {"polygon coordinates"});
            }
        }
    }
}
