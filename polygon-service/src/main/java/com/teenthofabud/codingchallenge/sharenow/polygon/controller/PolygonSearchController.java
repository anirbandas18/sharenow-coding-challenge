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
        LOGGER.info("Requesting all polygons");
        List<StrategicPolygonVO> voList = this.service.retrieveAll();
        ResponseEntity<List<StrategicPolygonVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons");
        return response;
    }

    @GetMapping("cityid/{cityId}")
    public ResponseEntity<?> getAllPolygonsByCityId(@PathVariable String cityId) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with cityId: {}", cityId);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByCityId(cityId);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with cityId: {}", cityId);
        return response;
    }

    @GetMapping("type/{type}")
    public ResponseEntity<?> getAllPolygonsByType(@PathVariable String type) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons of type: {}", type);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByType(type);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with type: {}", type);
        return response;
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getPolygonByName(@PathVariable String name) throws PolygonServiceException {
        LOGGER.info("Requesting all polygons with name: {}", name);
        List<StrategicPolygonDetailedVO> voList = this.service.retrieveByName(name);
        ResponseEntity<List<StrategicPolygonDetailedVO>> response = ResponseEntity.ok(voList);
        LOGGER.info("Responding with all available polygons with name: {}", name);
        return response;
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getPolygonById(@PathVariable String id) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with id: {}", id);
        StrategicPolygonDetailedVO vo = this.service.retrieveById(id);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of id: {}", id);
        return response;
    }

    @GetMapping("legacyid/{legacyId}")
    public ResponseEntity<?> getPolygonByCityId(@PathVariable String legacyId) throws PolygonServiceException {
        LOGGER.info("Requesting polygon with legacyId: {}", legacyId);
        StrategicPolygonDetailedVO vo = this.service.retrieveByLegacyId(legacyId);
        ResponseEntity<StrategicPolygonDetailedVO> response = ResponseEntity.ok(vo);
        LOGGER.info("Responding with polygon of legacyId: {}", legacyId);
        return response;
    }

}
