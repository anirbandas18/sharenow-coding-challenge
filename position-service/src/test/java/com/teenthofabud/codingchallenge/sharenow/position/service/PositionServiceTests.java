package com.teenthofabud.codingchallenge.sharenow.position.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.GeoFeatureDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionErrorCode;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.Car2StrategicPolygonPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.CarMappedVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.PositionVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.GeoFeatureVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygon2CarPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygonMappedVO;
import com.teenthofabud.codingchallenge.sharenow.position.repository.CarServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.repository.PolygonServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.service.impl.PositionServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
public class PositionServiceTests {

    @Mock
    private CarServiceClient carClient;

    @Mock
    private PolygonServiceClient polygonClient;

    @Mock
    private PlacementService placementService;

    @InjectMocks
    private PositionServiceImpl service = new PositionServiceImpl();

    private static List<CarDetailsDTO> LIST_OF_CAR_DTOS;

    private static CarDetailsDTO AB12XC34_DTO;
    private static CarDetailsDTO XZ67BO38_DTO;
    private static CarDetailsDTO QA19OP32_DTO;
    private static CarDetailsDTO UI45AD08_DTO;
    private static CarDetailsDTO YY87UI99_DTO;

    private static CarMappedVO AB12XC34_VO;
    private static CarMappedVO XZ67BO38_VO;
    private static CarMappedVO QA19OP32_VO;
    private static CarMappedVO UI45AD08_VO;
    private static CarMappedVO YY87UI99_VO;

    private static List<StrategicPolygonDetailedDTO> LIST_OF_STRATEGIC_POLYGON_DTOS;

    private static StrategicPolygonDetailedDTO HAUPTBAHNHOF_AREA_DTO;
    private static StrategicPolygonDetailedDTO ALTBAHNHOF_AREA_DTO;
    private static StrategicPolygonDetailedDTO FISHMARKT_AREA_DTO;

    private static StrategicPolygonMappedVO HAUPTBAHNHOF_AREA_VO;
    private static StrategicPolygonMappedVO ALTBAHNHOF_AREA_VO;
    private static StrategicPolygonMappedVO FISHMARKT_AREA_VO;

    private static String ASSUMED_HAUPTBAHNHOF_ID = "58a58bf685979b5415f3a39a";
    private static String ASSUMED_ALTBAHNHOF_ID = "58a58bf6766d51540f779930";
    private static String ASSUMED_FISHMARKT_ID = "58a58bdb766d51540f7794e2";


    @BeforeAll
    private static void setUp() {
        setUpCarTestData();
        setUpPolygonTestData();
    }

    private static void setUpCarTestData() {
        AB12XC34_DTO = new CarDetailsDTO(1, 10, "AB12XC34", "W-9876A", new PositionDTO(6.0d, 2.0d), 0.5f, "MERCEDES-C320"); // inside 1st
        XZ67BO38_DTO = new CarDetailsDTO(2, 10, "XZ67BO38", "W-9877A", new PositionDTO(7.5d, -5.0d), 0.2f, "JAGUAR-XF"); // on edge of 1st
        QA19OP32_DTO = new CarDetailsDTO(3, 10, "QA19OP32", "W-9988A", new PositionDTO(-50.0d, -30.0d), 0.1f, "RANGE-ROVER-DISCOVERY"); // outside all
        UI45AD08_DTO = new CarDetailsDTO(6, 10, "UI45AD08", "W-3321A", new PositionDTO(30.0d, 25.0d), 0.2f, "BMW-320i"); // inside 2nd
        YY87UI99_DTO = new CarDetailsDTO(9, 10, "YY87UI99", "W-5432Z", new PositionDTO(22.0d, 25.0d), 0.2f, "AUDI-Q3"); // inside 2nd
        LIST_OF_CAR_DTOS = Stream.of(AB12XC34_DTO, XZ67BO38_DTO, QA19OP32_DTO, UI45AD08_DTO, YY87UI99_DTO).collect(Collectors.toCollection(ArrayList::new));
        AB12XC34_VO = new CarMappedVO("AB12XC34", "MERCEDES-C320", "W-9876A", new PositionVO(2.0d, 6.0d));
        XZ67BO38_VO = new CarMappedVO("XZ67BO38", "JAGUAR-XF", "W-9877A", new PositionVO(-5.0d, 7.5d));
        QA19OP32_VO = new CarMappedVO("QA19OP32", "RANGE-ROVER-DISCOVERY", "W-9988A", new PositionVO(-30.0d, -50.0d));
        UI45AD08_VO = new CarMappedVO("UI45AD08", "BMW-320i", "W-3321A", new PositionVO(25.0d, 30.0d));
        YY87UI99_VO = new CarMappedVO("YY87UI99", "AUDI-Q3", "W-5432Z", new PositionVO(25.0d, 22.0d));
    }

    private static void setUpPolygonTestData() {
        String staticStrategicGeoJsonDumpFileName = "strategic-geojson-polygon-static-data.json";
        try {
            ObjectMapper om = new ObjectMapper();
            Resource staticStrategicGeoJsonDump = new ClassPathResource(staticStrategicGeoJsonDumpFileName);
            InputStream rawJson = staticStrategicGeoJsonDump.getInputStream();
            LIST_OF_STRATEGIC_POLYGON_DTOS = om.readValue(rawJson, new TypeReference<List<StrategicPolygonDetailedDTO>>() {});
            HAUPTBAHNHOF_AREA_DTO = LIST_OF_STRATEGIC_POLYGON_DTOS.stream().filter(spe -> spe.getId().compareTo(ASSUMED_HAUPTBAHNHOF_ID) == 0).findFirst().get();
            ALTBAHNHOF_AREA_DTO = LIST_OF_STRATEGIC_POLYGON_DTOS.stream().filter(spe -> spe.getId().compareTo(ASSUMED_ALTBAHNHOF_ID) == 0).findFirst().get();
            FISHMARKT_AREA_DTO = LIST_OF_STRATEGIC_POLYGON_DTOS.stream().filter(spe -> spe.getId().compareTo(ASSUMED_FISHMARKT_ID) == 0).findFirst().get();
            if(HAUPTBAHNHOF_AREA_DTO != null) {
                HAUPTBAHNHOF_AREA_VO = new StrategicPolygonMappedVO();
                HAUPTBAHNHOF_AREA_VO.setCityId(HAUPTBAHNHOF_AREA_DTO.getCityId());
                HAUPTBAHNHOF_AREA_VO.setId(HAUPTBAHNHOF_AREA_DTO.getId());
                HAUPTBAHNHOF_AREA_VO.setName(HAUPTBAHNHOF_AREA_DTO.getName());
                HAUPTBAHNHOF_AREA_VO.setType(HAUPTBAHNHOF_AREA_DTO.getType());
                HAUPTBAHNHOF_AREA_VO.setGeoFeatures(HAUPTBAHNHOF_AREA_DTO.getGeoFeatures().stream().map(gf -> geoFeatureDTO2VOConverter().convert(gf)).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("No strategic polygon with id " + ASSUMED_HAUPTBAHNHOF_ID + " is available as test data");
            }
            if(ALTBAHNHOF_AREA_DTO != null) {
                ALTBAHNHOF_AREA_VO = new StrategicPolygonMappedVO();
                ALTBAHNHOF_AREA_VO.setCityId(ALTBAHNHOF_AREA_DTO.getCityId());
                ALTBAHNHOF_AREA_VO.setId(ALTBAHNHOF_AREA_DTO.getId());
                ALTBAHNHOF_AREA_VO.setName(ALTBAHNHOF_AREA_DTO.getName());
                ALTBAHNHOF_AREA_VO.setType(ALTBAHNHOF_AREA_DTO.getType());
                ALTBAHNHOF_AREA_VO.setGeoFeatures(ALTBAHNHOF_AREA_DTO.getGeoFeatures().stream().map(gf -> geoFeatureDTO2VOConverter().convert(gf)).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("No strategic polygon with id " + ASSUMED_ALTBAHNHOF_ID + " is available as test data");
            }
            if(FISHMARKT_AREA_DTO != null) {
                FISHMARKT_AREA_VO = new StrategicPolygonMappedVO();
                FISHMARKT_AREA_VO.setCityId(FISHMARKT_AREA_DTO.getCityId());
                FISHMARKT_AREA_VO.setId(FISHMARKT_AREA_DTO.getId());
                FISHMARKT_AREA_VO.setName(FISHMARKT_AREA_DTO.getName());
                FISHMARKT_AREA_VO.setType(FISHMARKT_AREA_DTO.getType());
                FISHMARKT_AREA_VO.setGeoFeatures(FISHMARKT_AREA_DTO.getGeoFeatures().stream().map(gf -> geoFeatureDTO2VOConverter().convert(gf)).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("No strategic polygon with id " + ASSUMED_FISHMARKT_ID + " is available as test data");
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private static Converter<GeoFeatureDTO, GeoFeatureVO> geoFeatureDTO2VOConverter() {
        return (entity) -> {
            GeoFeatureVO vo = new GeoFeatureVO();
            if(entity != null) {
                vo.setGeometry(entity.getGeometry());
                vo.setName(entity.getName());
            }
            return vo;
        };
    }

    @BeforeEach
    private void init() {
        this.service.init();
        this.carClient = Mockito.mock(CarServiceClient.class);
        this.polygonClient = Mockito.mock(PolygonServiceClient.class);
        this.placementService = Mockito.mock(PlacementService.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test position of car by its vin within a strategic polygon")
    public void testPositioning_OfCar_ByVin_WithinA_StrategicPolygon() throws PositionServiceException {
        // arrange
        String vin = "AB12XC34";
        when(carClient.getCarDetailsByVin(vin)).thenReturn(AB12XC34_DTO);
        when(polygonClient.getAllPolygons()).thenReturn(LIST_OF_STRATEGIC_POLYGON_DTOS);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        Car2StrategicPolygonPositioningVO expectedVO = new Car2StrategicPolygonPositioningVO();
        expectedVO.setCar(AB12XC34_VO);
        expectedVO.setPolygon(HAUPTBAHNHOF_AREA_VO);
        Car2StrategicPolygonPositioningVO actualVO = null;

        // act
        try {
            actualVO = service.retrievePositionOfCarAndItsEnclosingPolygonByVin(vin);
        } catch (PositionServiceException psex) {
            // consuming exception because we know that this data won't cause any errors
        }

        //assert
        Assert.assertNotNull(actualVO);
        Assert.assertEquals(expectedVO.getCar().getVin(), actualVO.getCar().getVin());
        Assert.assertEquals(expectedVO.getCar().getModel(), actualVO.getCar().getModel());
        Assert.assertEquals(expectedVO.getCar().getNumberPlate(), actualVO.getCar().getNumberPlate());
        Assert.assertEquals(expectedVO.getPolygon().getCityId(), actualVO.getPolygon().getCityId());
        Assert.assertEquals(expectedVO.getPolygon().getId(), actualVO.getPolygon().getId());
        Assert.assertEquals(expectedVO.getPolygon().getName(), actualVO.getPolygon().getName());
        Assert.assertEquals(expectedVO.getPolygon().getType(), actualVO.getPolygon().getType());
    }

    @Test
    @DisplayName("Test position of car by its vin outside all strategic polygons")
    public void testPositioning_OfCar_ByVin_OutsideAll_StrategicPolygons() throws PositionServiceException {
        // arrange
        String vin = "QA19OP32";
        when(carClient.getCarDetailsByVin(vin)).thenReturn(QA19OP32_DTO);
        when(polygonClient.getAllPolygons()).thenReturn(LIST_OF_STRATEGIC_POLYGON_DTOS);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        Car2StrategicPolygonPositioningVO actualVO = null;
        PositionServiceException psex = null;
        PositionErrorCode expectedErrorCode = PositionErrorCode.NOT_FOUND;

        // act
        try {
            actualVO = service.retrievePositionOfCarAndItsEnclosingPolygonByVin(vin);
        } catch (PositionServiceException e) {
            psex = e;
        }

        //assert
        Assert.assertNull(actualVO);
        Assert.assertNotNull(psex);
        Assert.assertEquals(PositionErrorCode.NOT_FOUND, psex.getError());
        Assert.assertEquals("Unplaced car", psex.getMessage());
        Assert.assertEquals(expectedErrorCode, psex.getError());
        Assert.assertEquals(expectedErrorCode.getErrorCode(), psex.getError().getErrorCode());
        Assert.assertEquals(expectedErrorCode.getStatusCode(), psex.getError().getStatusCode());
    }

    @Test
    @DisplayName("Test position of all within a strategic polygon by its polygon id")
    public void testPosition_OfAllCars_WithinA_StrategicPolygon_ByItsPolygonId() throws PositionServiceException {
        // arrange
        String polygonId = ASSUMED_ALTBAHNHOF_ID;
        when(polygonClient.getPolygonDetailsById(polygonId)).thenReturn(ALTBAHNHOF_AREA_DTO);
        when(carClient.getAllCarsWithDetails()).thenReturn(LIST_OF_CAR_DTOS);
        when(placementService.isCarInsidePolygon(UI45AD08_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(YY87UI99_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(XZ67BO38_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        StrategicPolygon2CarPositioningVO expectedVO = new StrategicPolygon2CarPositioningVO();
        expectedVO.addCar(UI45AD08_VO);
        expectedVO.addCar(YY87UI99_VO);
        expectedVO.setPolygon(ALTBAHNHOF_AREA_VO);
        StrategicPolygon2CarPositioningVO actualVO = null;

        // act
        try {
            actualVO = service.retrievePositionsOfAllCarsWithinPolygonByPolygonId(polygonId);
        } catch (PositionServiceException psex) {
            // consuming exception because we know that this data won't cause any errors
        }

        //assert
        Assert.assertNotNull(actualVO);
        Assert.assertEquals(expectedVO.getCars().get(0).getVin(), actualVO.getCars().get(0).getVin());
        Assert.assertEquals(expectedVO.getCars().get(0).getModel(), actualVO.getCars().get(0).getModel());
        Assert.assertEquals(expectedVO.getCars().get(0).getNumberPlate(), actualVO.getCars().get(0).getNumberPlate());
        Assert.assertEquals(expectedVO.getCars().get(1).getVin(), actualVO.getCars().get(1).getVin());
        Assert.assertEquals(expectedVO.getCars().get(1).getModel(), actualVO.getCars().get(1).getModel());
        Assert.assertEquals(expectedVO.getCars().get(1).getNumberPlate(), actualVO.getCars().get(1).getNumberPlate());
        Assert.assertEquals(expectedVO.getPolygon().getCityId(), actualVO.getPolygon().getCityId());
        Assert.assertEquals(expectedVO.getPolygon().getId(), actualVO.getPolygon().getId());
        Assert.assertEquals(expectedVO.getPolygon().getName(), actualVO.getPolygon().getName());
        Assert.assertEquals(expectedVO.getPolygon().getType(), actualVO.getPolygon().getType());
    }

    @Test
    @DisplayName("Test position of all within a strategic polygon by its name")
    public void testPosition_OfAllCars_WithinA_StrategicPolygon_ByItsName() throws PositionServiceException {
        // arrange
        String name = "bahnhof";
        when(polygonClient.getAllPolygonsByName(name)).thenReturn(Stream.of(
                HAUPTBAHNHOF_AREA_DTO, ALTBAHNHOF_AREA_DTO).collect(Collectors.toCollection(ArrayList::new)));
        when(carClient.getAllCarsWithDetails()).thenReturn(LIST_OF_CAR_DTOS);
        when(placementService.isCarInsidePolygon(UI45AD08_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(YY87UI99_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(XZ67BO38_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, HAUPTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(UI45AD08_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(YY87UI99_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(true);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(XZ67BO38_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, ALTBAHNHOF_AREA_DTO)).thenReturn(false);
        Set<StrategicPolygon2CarPositioningVO> expectedVOSet = new TreeSet<>();
        StrategicPolygon2CarPositioningVO expectedVO1 = new StrategicPolygon2CarPositioningVO();
        expectedVO1.addCar(AB12XC34_VO);
        expectedVO1.addCar(XZ67BO38_VO);
        expectedVO1.setPolygon(HAUPTBAHNHOF_AREA_VO);
        expectedVOSet.add(expectedVO1);
        StrategicPolygon2CarPositioningVO expectedVO2 = new StrategicPolygon2CarPositioningVO();
        expectedVO2.addCar(UI45AD08_VO);
        expectedVO2.addCar(YY87UI99_VO);
        expectedVO2.setPolygon(ALTBAHNHOF_AREA_VO);
        expectedVOSet.add(expectedVO2);
        Set<StrategicPolygon2CarPositioningVO> actualVOSet = new TreeSet<>();

        // act
        try {
            actualVOSet = service.retrievePositionsOfAllCarsWithinPolygonByPolygonName(name);
        } catch (PositionServiceException psex) {
            // consuming exception because we know that this data won't cause any errors
        }
        List<StrategicPolygon2CarPositioningVO> expectedVOList = new ArrayList<>(expectedVOSet);
        List<StrategicPolygon2CarPositioningVO> actualVOList = new ArrayList<>(actualVOSet);

        //assert

        Assert.assertEquals(false, actualVOList.isEmpty());
        Assert.assertEquals(2, actualVOList.size());

        Assert.assertEquals(expectedVOList.get(0).getCars().get(0).getVin(), actualVOList.get(0).getCars().get(0).getVin());
        Assert.assertEquals(expectedVOList.get(0).getCars().get(1).getVin(), actualVOList.get(0).getCars().get(1).getVin());
        Assert.assertEquals(expectedVOList.get(0).getPolygon().getId(), actualVOList.get(0).getPolygon().getId());

        Assert.assertEquals(expectedVOList.get(1).getCars().get(0).getVin(), actualVOList.get(1).getCars().get(0).getVin());
        Assert.assertEquals(expectedVOList.get(1).getCars().get(1).getVin(), actualVOList.get(1).getCars().get(1).getVin());
        Assert.assertEquals(expectedVOList.get(1).getPolygon().getId(), actualVOList.get(1).getPolygon().getId());
    }

    @Test
    @DisplayName("Test position no cars available within a strategic polygon by its polygon id")
    public void testPosition_NoCarsAvailable_WithinA_StrategicPolygon_ByItsPolygonId() throws PositionServiceException {
        // arrange
        String polygonId = ASSUMED_FISHMARKT_ID;
        when(polygonClient.getPolygonDetailsById(polygonId)).thenReturn(FISHMARKT_AREA_DTO);
        when(carClient.getAllCarsWithDetails()).thenReturn(LIST_OF_CAR_DTOS);
        when(placementService.isCarInsidePolygon(UI45AD08_DTO, FISHMARKT_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(YY87UI99_DTO, FISHMARKT_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(AB12XC34_DTO, FISHMARKT_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(XZ67BO38_DTO, FISHMARKT_AREA_DTO)).thenReturn(false);
        when(placementService.isCarInsidePolygon(QA19OP32_DTO, FISHMARKT_AREA_DTO)).thenReturn(false);

        StrategicPolygon2CarPositioningVO actualVO = null;
        PositionServiceException psex = null;
        PositionErrorCode expectedErrorCode = PositionErrorCode.NOT_FOUND;

        // act
        try {
            actualVO = service.retrievePositionsOfAllCarsWithinPolygonByPolygonId(polygonId);
        } catch (PositionServiceException e) {
            psex = e;
        }

        //assert
        Assert.assertNull(actualVO);
        Assert.assertNotNull(psex);
        Assert.assertEquals(PositionErrorCode.NOT_FOUND, psex.getError());
        Assert.assertEquals("No cars available", psex.getMessage());
        Assert.assertEquals(expectedErrorCode, psex.getError());
        Assert.assertEquals(expectedErrorCode.getErrorCode(), psex.getError().getErrorCode());
        Assert.assertEquals(expectedErrorCode.getStatusCode(), psex.getError().getStatusCode());
    }

}
