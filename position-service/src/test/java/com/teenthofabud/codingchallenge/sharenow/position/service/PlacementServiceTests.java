package com.teenthofabud.codingchallenge.sharenow.position.service;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionErrorCode;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.service.impl.PlacementServiceGFGImpl;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PlacementServiceTests {

    private static PlacementService SERVICE;

    private static CarDetailsDTO CAR;

    private static StrategicPolygonDetailedDTO STRATEGIC_POLYGON;

    @BeforeAll
    public static void init() {
        SERVICE = new PlacementServiceGFGImpl();
        CAR = new CarDetailsDTO();
        STRATEGIC_POLYGON = new StrategicPolygonDetailedDTO();
    }

    @Test
    @DisplayName("Test car lies inside the strategic polygon")
    public void testCarLies_InsideTheStrategicPolygon() {
        // arrange
        PositionDTO carPosition = new PositionDTO(9.137148666666667d, 48.79031233333333d);
        List<LngLatAlt> polygonCoordinates = Arrays.asList(
                new LngLatAlt(9.137248d, 48.790411d),
                new LngLatAlt( 9.137248d, 48.790263d),
                new LngLatAlt(9.13695d, 48.790263d),
                new LngLatAlt(9.137248d, 48.790411d));
        CAR.setPosition(carPosition);
        STRATEGIC_POLYGON.setGeometry(new Polygon(polygonCoordinates));
        boolean actualResult = false;

        // act
        try {
            actualResult = SERVICE.isCarInsidePolygonConsiderOnlyOuterRing(CAR, STRATEGIC_POLYGON);
        } catch (PositionServiceException psex) {
            // consume exception because we know this data will not throw any exception
        }

        //assert
        Assert.assertEquals(true, actualResult);
    }

    @Test
    @DisplayName("Test car lies outside the strategic polygon")
    public void testCarLies_OutsideTheStrategicPolygon() {
        // arrange
        PositionDTO carPosition = new PositionDTO(20.0d, 20.0d);
        List<LngLatAlt> polygonCoordinates = Arrays.asList(new LngLatAlt(0.0d, 0.0d), new LngLatAlt(10.0d, 0.0d),
                new LngLatAlt(10.0d, 10.0d), new LngLatAlt(0.0d, 10.0d));
        CAR.setPosition(carPosition);
        STRATEGIC_POLYGON.setGeometry(new Polygon(polygonCoordinates));
        boolean actualResult = true;

        // act
        try {
            actualResult = SERVICE.isCarInsidePolygonConsiderOnlyOuterRing(CAR, STRATEGIC_POLYGON);
        } catch (PositionServiceException psex) {
            // consume exception because we know this data will not throw any exception
        }

        //assert
        Assert.assertEquals(false, actualResult);
    }

    @Test
    @DisplayName("Test car lies exactly on an edge of the strategic polygon")
    public void testCarLies_ExactlyOnAnEdge_OfTheStrategicPolygon() {
        // arrange
        PositionDTO carPosition = new PositionDTO(5.0d, 0.0d);
        List<LngLatAlt> polygonCoordinates = Arrays.asList(new LngLatAlt(0.0d, 0.0d), new LngLatAlt(10.0d, 0.0d),
                new LngLatAlt(10.0d, 10.0d), new LngLatAlt(0.0d, 10.0d));
        CAR.setPosition(carPosition);
        STRATEGIC_POLYGON.setGeometry(new Polygon(polygonCoordinates));
        boolean actualResult = false;

        // act
        try {
            actualResult = SERVICE.isCarInsidePolygonConsiderOnlyOuterRing(CAR, STRATEGIC_POLYGON);
        } catch (PositionServiceException psex) {
            // consume exception because we know this data will not throw any exception
        }

        //assert
        Assert.assertEquals(true, actualResult);
    }

    @Test
    @DisplayName("Test car lies outside the strategic polygon but is collinear to one of its vertex")
    public void testCarLiesOutside_TheStrategicPolygon_ButIsCollinear_ToOneOfItsVertex() {
        // arrange
        PositionDTO carPosition = new PositionDTO(-5.0d, 5.0d);
        List<LngLatAlt> polygonCoordinates = Arrays.asList(new LngLatAlt(0.0d, 0.0d), new LngLatAlt(10.0d, 0.0d),
                new LngLatAlt(10.0d, 10.0d), new LngLatAlt(0.0d, 10.0d));
        CAR.setPosition(carPosition);
        STRATEGIC_POLYGON.setGeometry(new Polygon(polygonCoordinates));
        boolean actualResult = true;

        // act
        try {
            actualResult = SERVICE.isCarInsidePolygonConsiderOnlyOuterRing(CAR, STRATEGIC_POLYGON);
        } catch (PositionServiceException psex) {
            // consume exception because we know this data will not throw any exception
        }

        //assert
        Assert.assertEquals(false, actualResult);
    }

    @Test
    @DisplayName("Test service throws exception if strategic polygon provided is not a polygon in nature")
    public void testReportError_IfStrategicPolygon_IsNotAPolygon_InNature() {
        // arrange
        PositionDTO carPosition = new PositionDTO(5.0d, 2.0d);
        List<LngLatAlt> polygonCoordinates = Arrays.asList(new LngLatAlt(0.0d, 0.0d), new LngLatAlt(10.0d, 0.0d));
        CAR.setPosition(carPosition);
        STRATEGIC_POLYGON.setGeometry(new Polygon(polygonCoordinates));
        boolean actualResult = true;
        String expectedMessage = "Invalid polygon";
        PositionErrorCode expectedErrorCode = PositionErrorCode.UNEXPECTED_PARAMETER;
        String expectedParameterMessage =  "polygon vertex count < 3";

        String actualMessage = "";
        PositionErrorCode actualErrorCode = null;
        String actualParameterMessage = "";


        // act
        try {
            actualResult = SERVICE.isCarInsidePolygonConsiderOnlyOuterRing(CAR, STRATEGIC_POLYGON);
        } catch (PositionServiceException psex) {
            actualErrorCode = psex.getError();
            actualMessage = psex.getMessage();
            actualParameterMessage = (String) psex.getParams()[0];
        }

        //assert
        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(expectedErrorCode, actualErrorCode);
        Assert.assertEquals(expectedParameterMessage, actualParameterMessage);
    }


}
