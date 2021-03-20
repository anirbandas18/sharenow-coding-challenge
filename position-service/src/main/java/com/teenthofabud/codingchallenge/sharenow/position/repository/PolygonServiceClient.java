package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "polygonServiceClient", url = "${poss.polygon-service.url}")
public interface PolygonServiceClient {

    @GetMapping("/search")
    public List<StrategicPolygonDetailedDTO> getAllPolygons();

    @GetMapping("/search/name/{name}")
    public List<StrategicPolygonDetailedDTO> getAllPolygonsByName(@PathVariable String name);

    @GetMapping("/search/id/{id}")
    public StrategicPolygonDetailedDTO getPolygonDetailsById(@PathVariable String id);

}