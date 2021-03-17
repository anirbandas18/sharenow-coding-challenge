package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "carServiceClient", url = "${poss.car-service.url}")
public interface CarServiceClient {

    @GetMapping("search")
    public List<CarVO> getAllCars();

    @GetMapping("search/vin/{vin}")
    public List<CarVO> getCarDetailsByVin(@PathVariable String vin);

}
