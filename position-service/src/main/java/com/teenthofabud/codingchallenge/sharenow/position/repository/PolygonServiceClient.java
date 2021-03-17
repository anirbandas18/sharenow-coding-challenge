package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "polygonServiceClient", url = "${poss.polygon-service.url}")
public interface PolygonServiceClient {

    @GetMapping("search")
    public List<CarVO> getAllPolygons();

    @GetMapping("search/cityid/{cityId}")
    public List<CarVO> getAllPolygonsByCityId(@PathVariable String cityId);

    @GetMapping("search/name/{name}")
    public List<CarVO> getAllPolygonsByName(@PathVariable String name);

    @GetMapping("search/id/{id}")
    public List<CarVO> getPolygonDetailsById(@PathVariable String id);

}