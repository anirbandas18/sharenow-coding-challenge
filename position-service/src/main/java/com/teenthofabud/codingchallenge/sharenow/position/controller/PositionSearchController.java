package com.teenthofabud.codingchallenge.sharenow.position.controller;

import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.ErrorVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.Car2StrategicPolygonPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygon2CarPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.service.PositionService;
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
@Tag(name = "Car to strategic polygon positioning API", description = "Position car to a strategic polygon or list the cars within a strategic polygon")
public class PositionSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionSearchController.class);

    @Autowired
    private PositionService service;

    @Operation(summary = "Get car by its VIN and the strategic polygons enclosing it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mapping from car to its enclosing strategic polygon",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Car2StrategicPolygonPositioningVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Car VIN is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No cars available having this VIN",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("car/vin/{vin}")
    public ResponseEntity<?> getVehicleAndItsEnclosingStrategicPolygon(
            @Parameter(description = "VIN of the car to be searched for") @PathVariable String vin) throws PositionServiceException {
        LOGGER.info("Determining polygon enclosing the car with vin");
        Car2StrategicPolygonPositioningVO vo = this.service.retrievePositionOfCarAndItsEnclosingPolygonByVin(vin);
        ResponseEntity<Car2StrategicPolygonPositioningVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Determined enclosed polygon for the car with vin");
        return response;
    }

    @Operation(summary = "Get strategic polygon by its id and the cars it contains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mapping from strategic polygon to the cars that it encloses",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StrategicPolygon2CarPositioningVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon id is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons or cars are available by this id of the strategic polygon",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("polygon/id/{id}")
    public ResponseEntity<?> getStrategicPolygonAndTheVehiclesItContains(
            @Parameter(description = "id of the strategic polygon to be searched for") @PathVariable String id) throws PositionServiceException {
        LOGGER.info("Determining all cars enclosed within the polygon by id");
        StrategicPolygon2CarPositioningVO vo = this.service.retrievePositionsOfAllCarsWithinPolygonByPolygonId(id);
        ResponseEntity<StrategicPolygon2CarPositioningVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Determined all cars enclosed within this polygon by its id");
        return response;
    }

    @Operation(summary = "Get all strategic polygon by this name and with each one containing the cars that it encloses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of mapping from each strategic polygon to the cars that it encloses",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StrategicPolygon2CarPositioningVO.class))) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon name is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons or cars are available by this name of the strategic polygon",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("polygon/name/{name}")
    public ResponseEntity<?> getStrategicPolygonByNameAndTheVehiclesTheyContain(
            @Parameter(description = "name of the strategic polygon to be searched for") @PathVariable String name) throws PositionServiceException {
        LOGGER.info("Determining all cars enclosed within the polygons by name");
        Set<StrategicPolygon2CarPositioningVO> voList = this.service.retrievePositionsOfAllCarsWithinPolygonByPolygonName(name);
        ResponseEntity<Set<StrategicPolygon2CarPositioningVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Determined all cars enclosed within the polygons by their name");
        return response;
    }

}
