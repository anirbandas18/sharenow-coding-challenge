package com.teenthofabud.codingchallenge.sharenow.polygon.controller;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonDetailedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.service.PolygonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class PolygonSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonSearchController.class);

    @Autowired
    private PolygonService service;

    @GetMapping
    public ResponseEntity<?> getAllPolygons() {
        LOGGER.info("Requesting all polygons and their details");
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveAll();
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons and their details");
        return response;
    }

    @GetMapping("cityid/{cityId}")
    public ResponseEntity<?> getAllPolygonsByCityId(@PathVariable String cityId) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with cityId: {} and their details", cityId);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByCityId(cityId);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with cityId: {} and their details", cityId);
        return response;
    }

    @GetMapping("type/{type}")
    public ResponseEntity<?> getAllPolygonsByType(@PathVariable String type) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons of type: {} and their details", type);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByType(type);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with type: {} and their details", type);
        return response;
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getPolygonByName(@PathVariable String name) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with name: {} and their details", name);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByName(name);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with name: {} and their details", name);
        return response;
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getPolygonById(@PathVariable String id) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with id: {} and its details", id);
        StrategicPolygonDetailedVO vo = this.service.retrieveById(id);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of id: {} and its details", id);
        return response;
    }

    @GetMapping("legacyid/{legacyId}")
    public ResponseEntity<?> getPolygonByCityId(@PathVariable String legacyId) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with legacyId: {} and their details", legacyId);
        StrategicPolygonDetailedVO vo = this.service.retrieveByLegacyId(legacyId);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of legacyId: {} and their details", legacyId);
        return response;
    }

}
