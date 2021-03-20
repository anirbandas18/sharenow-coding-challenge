package com.teenthofabud.codingchallenge.sharenow.car;

import com.teenthofabud.codingchallenge.sharenow.car.service.impl.CarServiceImpl;
import com.teenthofabud.codingchallenge.sharenow.car.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.entity.PositionEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarErrorCode;
import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarServiceException;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.PositionVO;
import com.teenthofabud.codingchallenge.sharenow.car.repository.CarRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
public class CarServiceTests {

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarServiceImpl service = new CarServiceImpl();

    private static List<CarEntity> LIST_OF_CAR_ENTITIES;

    private static CarEntity AB12XC34_ENTITY;
    private static CarEntity XZ67BO38_ENTITY;
    private static CarEntity QA19OP32_ENTITY;
    private static CarEntity UI45AD08_ENTITY;

    private static CarDetailsVO AB12XC34_VO;
    private static CarDetailsVO XZ67BO38_VO;
    private static CarDetailsVO QA19OP32_VO;
    private static CarDetailsVO UI45AD08_VO;


    @BeforeAll
    private static void setUp() {
        AB12XC34_ENTITY = new CarEntity(1, 12, "AB12XC34", "W-9876A", new PositionEntity(5.0d, 10.0d), 0.5f, "MERCEDES-C320", new Date());
        XZ67BO38_ENTITY = new CarEntity(2, 12, "XZ67BO38", "W-9877A", new PositionEntity(15.0d, 20.0d), 0.2f, "JAGUAR-XF", new Date());
        QA19OP32_ENTITY = new CarEntity(3, 11, "QA19OP32", "W-9988A", new PositionEntity(5.0d, 30.0d), 0.1f, "RANGE-ROVER-DISCOVERY", new Date());
        UI45AD08_ENTITY = new CarEntity(6, 10, "UI45AD08", "W-3321A", new PositionEntity(22.0d, 10.0d), 0.2f, "BMW-320i", new Date());
        LIST_OF_CAR_ENTITIES = Arrays.asList(AB12XC34_ENTITY, XZ67BO38_ENTITY, QA19OP32_ENTITY, UI45AD08_ENTITY);
        AB12XC34_VO = new CarDetailsVO(1, 12, "AB12XC34", "W-9876A", new PositionVO(5.0d, 10.0d), 0.5f, "MERCEDES-C320");
        XZ67BO38_VO = new CarDetailsVO(2, 12, "XZ67BO38", "W-9877A", new PositionVO(15.0d, 20.0d), 0.2f, "JAGUAR-XF");
        QA19OP32_VO = new CarDetailsVO(3, 11, "QA19OP32", "W-9988A", new PositionVO(5.0d, 30.0d), 0.1f, "RANGE-ROVER-DISCOVERY");
        UI45AD08_VO = new CarDetailsVO(6, 10, "UI45AD08", "W-3321A", new PositionVO(22.0d, 10.0d), 0.2f, "BMW-320i");
    }

    @BeforeEach
    private void init() {
        this.repository = Mockito.mock(CarRepository.class);
        MockitoAnnotations.initMocks(this);
        this.service.init();

    }

    @Test
    @DisplayName("Test retrieve all cars are available")
    void testRetrieve_AllCars_AreAvailable() {
        // arrange
        when(repository.findAll()).thenReturn(LIST_OF_CAR_ENTITIES);

        // act
        Set<CarDetailsVO> listOfCarVOs = service.retrieveAllCarsWithDetails();

        // assert
        Assert.assertNotNull(listOfCarVOs);
        Assert.assertEquals(LIST_OF_CAR_ENTITIES.size(), listOfCarVOs.size());
    }

    @Test
    @DisplayName("Test retrieve no cars are available")
    void testRetrieve_NoCars_AreAvailable() {
        // arrange
        when(repository.findAll()).thenReturn(new TreeSet<>());

        // act
        Set<CarDetailsVO> listOfCarVOs = service.retrieveAllCarsWithDetails();

        // assert
        Assert.assertNotNull(listOfCarVOs);
        Assert.assertEquals(0, listOfCarVOs.size());
    }

    @Test
    @DisplayName("Test throw exception when vin of car is not available")
    void testThrowException_WhenVinOfCar_IsNotAvailable() {
        // arrange
        when(repository.findById("Car:VV56NH88")).thenReturn(Optional.empty());
        CarDetailsVO carVO = null;
        CarServiceException csex = null;
        CarErrorCode errorCode = CarErrorCode.NOT_FOUND;


        // act
        try {
            carVO = service.retrieveCarDetailsByVin("VV56NH88");
        } catch (CarServiceException e) {
            csex = e;
        }

        // assert
        Assert.assertNull(carVO);
        Assert.assertNotNull(csex);
        Assert.assertEquals("No car found that matches with vin", csex.getMessage());
        Assert.assertEquals(errorCode.getErrorCode(), csex.getError().getErrorCode());
        Assert.assertEquals(errorCode.getStatusCode(), csex.getError().getStatusCode());
    }

    @Test
    @DisplayName("Test throw exception when vin of car is invalid")
    void testThrowException_WhenVinOfCar_IsInvalid() {
        // arrange
        when(repository.findById("")).thenReturn(Optional.empty());
        CarDetailsVO carVO = null;
        CarServiceException csex = null;
        CarErrorCode errorCode = CarErrorCode.INVALID_PARAMETER;

        // act
        try {
            carVO = service.retrieveCarDetailsByVin("");
        } catch (CarServiceException e) {
            csex = e;
        }

        // assert
        Assert.assertNull(carVO);
        Assert.assertNotNull(csex);
        Assert.assertEquals("Invalid vin", csex.getMessage());
        Assert.assertEquals(errorCode.getErrorCode(), csex.getError().getErrorCode());
        Assert.assertEquals(errorCode.getStatusCode(), csex.getError().getStatusCode());
    }

    @Test
    @DisplayName("Test retrieve details of car by its vin")
    void testRetrieve_DetailsOfCar_ByItsVin() {
        // arrange
        CarEntity searchEntity = new CarEntity(1, 12, "AB12XC34", "W-9876A", new PositionEntity(5.0d, 10.0d), 0.5f, "MERCEDES-C320", new Date());
        String vin = "AB12XC34";
        when(repository.findById(vin)).thenReturn(Optional.of(searchEntity));
        CarDetailsVO carVO = null;

        // act
        try {
            carVO = service.retrieveCarDetailsByVin(vin);
        } catch (CarServiceException e) {
            // consume exception here because we know that this data will not cause any exception
        }

        // assert
        Assert.assertNotNull(carVO);
        Assert.assertEquals(String.valueOf(AB12XC34_ENTITY.getFuel()), String.valueOf(carVO.getFuel()));
        Assert.assertEquals(AB12XC34_ENTITY.getId(), carVO.getId());
        Assert.assertEquals(AB12XC34_ENTITY.getLocationId(), carVO.getLocationId());
        Assert.assertEquals(AB12XC34_ENTITY.getModel(), carVO.getModel());
        Assert.assertEquals(AB12XC34_ENTITY.getNumberPlate(), carVO.getNumberPlate());
        Assert.assertEquals(AB12XC34_ENTITY.getVin(), carVO.getVin());
        Assert.assertEquals(String.valueOf(AB12XC34_ENTITY.getFuel()), String.valueOf(carVO.getFuel()));
        Assert.assertEquals(String.valueOf(AB12XC34_ENTITY.getPosition().getLatitude()),
                String.valueOf(carVO.getPosition().getLatitude()));
        Assert.assertEquals(String.valueOf(AB12XC34_ENTITY.getPosition().getLongitude()),
                String.valueOf(carVO.getPosition().getLongitude()));

    }

}
