package com.teenthofabud.codingchallenge.sharenow.car.controller;

import com.teenthofabud.codingchallenge.sharenow.car.model.error.CarServiceException;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarVO;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.ErrorVO;
import com.teenthofabud.codingchallenge.sharenow.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("search")
@Tag(name = "Car search API", description = "Search cars by its attributes")
public class CarSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchController.class);

    @Autowired
    private CarService service;

    @Operation(summary = "Get details of a car by vin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details of a car matched by its vin",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarDetailsVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Car vin id is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No car is available with this vin",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("vin/{vin}")
    public ResponseEntity<?> getCarByVin(
            @Parameter(description = "VIN of the car to be searched for") @PathVariable String vin) throws CarServiceException {
        LOGGER.info("Requesting car with vin: {}", vin);
        CarDetailsVO vo = this.service.retrieveCarDetailsByVin(vin);
        ResponseEntity<CarDetailsVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with car of vin: {}", vin);
        return response;
    }

    @Operation(summary = "Get all available cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all available cars",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarVO.class))) }),
            @ApiResponse(responseCode = "204", description = "No cars are available")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<?> getAllCars() throws CarServiceException {
        LOGGER.info("Requesting all cars");
        List<CarVO> voList = this.service.retrieveAllCars();
        ResponseEntity<List<CarVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available cars");
        return response;
    }

    @Operation(summary = "Get all available cars with their individual details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all available car with detailss",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarDetailsVO.class))) }),
            @ApiResponse(responseCode = "204", description = "No cars are available")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("withdetails")
    public ResponseEntity<?> getAllCarsWithDetails() throws CarServiceException {
        LOGGER.info("Requesting all cars and their details");
        Set<CarDetailsVO> voList = this.service.retrieveAllCarsWithDetails();
        ResponseEntity<Set<CarDetailsVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available cars and their details");
        return response;
    }

}
