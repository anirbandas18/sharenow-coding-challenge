package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@FeignClient(value = "polygonServiceClient", url = "${poss.polygon-service.url}")
public interface PolygonServiceClient {

    @GetMapping("/search")
    public List<StrategicPolygonDetailedDTO> getAllPolygons();

    @GetMapping("/search/name/{name}")
    public List<StrategicPolygonDetailedDTO> getAllPolygonsByName(@PathVariable String name);

    @GetMapping("/search/id/{id}")
    public StrategicPolygonDetailedDTO getPolygonDetailsById(@PathVariable String id);

    static List<StrategicPolygonDetailedDTO> getDefaultStrategicPolygons() {
        return new ArrayList<StrategicPolygonDetailedDTO>();
    }

    static StrategicPolygonDetailedDTO getDefaultStrategicPolygonDetailsById(String polygonId) {
        StrategicPolygonDetailedDTO dto = new StrategicPolygonDetailedDTO();
        dto.setId(polygonId);
        return dto;
    }

    static List<StrategicPolygonDetailedDTO> getDefaultStrategicPolygonsAndTheirDetailsByName(String name) {
        StrategicPolygonDetailedDTO dto = new StrategicPolygonDetailedDTO();
        dto.setName(name);
        return Arrays.asList(dto);
    }

}