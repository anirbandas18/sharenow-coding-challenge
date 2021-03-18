package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "polygonServiceClient", url = "${poss.polygon-service.url}")
public interface PolygonServiceClient {

    @GetMapping("polygon-service/search")
    public List<StrategicPolygonDetailedDTO> getAllPolygons();

    @GetMapping("polygon-service/search/cityid/{cityId}")
    public List<StrategicPolygonDetailedDTO> getAllPolygonsByCityId(@PathVariable String cityId);

    @GetMapping("polygon-service/search/name/{name}")
    public List<StrategicPolygonDetailedDTO> getAllPolygonsByName(@PathVariable String name);

    @GetMapping("polygon-service/search/id/{id}")
    public StrategicPolygonDetailedDTO getPolygonDetailsById(@PathVariable String id);

}