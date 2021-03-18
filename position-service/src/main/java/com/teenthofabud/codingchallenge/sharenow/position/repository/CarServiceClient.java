package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "carServiceClient", url = "${poss.car-service.url}")
public interface CarServiceClient {

    @GetMapping("car-service/search")
    public List<CarDTO> getAllCars();

    @GetMapping("car-service/search/vin/{vin}")
    public CarDetailsDTO getCarDetailsByVin(@PathVariable String vin);

    @GetMapping("car-service/search/withdetails")
    public List<CarDetailsDTO> getAllCarsWithDetails();

}
