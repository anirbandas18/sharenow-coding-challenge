package com.teenthofabud.codingchallenge.sharenow.polygon.controller;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.ErrorVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonDetailedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.service.PolygonService;
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

@RestController
@RequestMapping("search")
@Tag(name = "Strategic polygon search API", description = "Search strategic polygons by its attributes")
public class PolygonSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonSearchController.class);

    @Autowired
    private PolygonService service;

    @Operation(summary = "Get all available strategic polygons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all available strategic polygons",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StrategicPolygonDetailedVO.class))) }),
            @ApiResponse(responseCode = "204", description = "No strategic polygons are available")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<?> getAllPolygons() {
        LOGGER.info("Requesting all polygons and their details");
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveAll();
        ResponseEntity<?> response = null;
        if(voList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = ResponseEntity.ok(voList);
        }
        LOGGER.info("Responding with all available polygons and their details");
        return response;
    }

    @Operation(summary = "Get all matching strategic polygons by cityId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of strategic polygons matched by cityId",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StrategicPolygonDetailedVO.class))) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon cityId is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons available having this cityId",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("cityid/{cityId}")
    public ResponseEntity<?> getAllPolygonsByCityId(
            @Parameter(description = "cityId of the strategic polygon to be searched for") @PathVariable String cityId) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with cityId: {} and their details", cityId);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByCityId(cityId);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with cityId: {} and their details", cityId);
        return response;
    }

    @Operation(summary = "Get all matching strategic polygons by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of strategic polygons matched by type",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StrategicPolygonDetailedVO.class))) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon type is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons available of this type",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("type/{type}")
    public ResponseEntity<?> getAllPolygonsByType(
            @Parameter(description = "type of the strategic polygon to be searched for") @PathVariable String type) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons of type: {} and their details", type);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByType(type);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with type: {} and their details", type);
        return response;
    }

    @Operation(summary = "Get all matching strategic polygons by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of strategic polygons matched by name",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StrategicPolygonDetailedVO.class)) ) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon name is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons available with this name",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("name/{name}")
    public ResponseEntity<?> getPolygonByName(
            @Parameter(description = "name of the strategic polygon to be searched for") @PathVariable String name) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with name: {} and their details", name);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByName(name);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with name: {} and their details", name);
        return response;
    }

    @Operation(summary = "Get details of strategic polygons by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details of strategic polygons matched by id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StrategicPolygonDetailedVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon id is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons available with this id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("id/{id}")
    public ResponseEntity<?> getPolygonById(
            @Parameter(description = "id of the strategic polygon to be searched for") @PathVariable String id) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with id: {} and its details", id);
        StrategicPolygonDetailedVO vo = this.service.retrieveById(id);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of id: {} and its details", id);
        return response;
    }

    @Operation(summary = "Get details of strategic polygons by legacyId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details of strategic polygons matched by legacyId",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StrategicPolygonDetailedVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Strategic polygon legacyId is invalid",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) }),
            @ApiResponse(responseCode = "404", description = "No strategic polygons available with this legacyId",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorVO.class)) })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("legacyid/{legacyId}")
    public ResponseEntity<?> getPolygonByCityId(
            @Parameter(description = "legacyId of the strategic polygon to be searched for") @PathVariable String legacyId) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with legacyId: {} and their details", legacyId);
        StrategicPolygonDetailedVO vo = this.service.retrieveByLegacyId(legacyId);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of legacyId: {} and their details", legacyId);
        return response;
    }

}
