package com.teenthofabud.codingchallenge.sharenow.position.repository;

import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value = "carServiceClient", url = "${poss.car-service.url}")
public interface CarServiceClient {

    @GetMapping("/search")
    public List<CarDTO> getAllCars();

    @GetMapping("/search/vin/{vin}")
    public CarDetailsDTO getCarDetailsByVin(@PathVariable String vin);

    @GetMapping("/search/withdetails")
    public List<CarDetailsDTO> getAllCarsWithDetails();

    static CarDetailsDTO getDefaultCarDetailsByVin(String vin) {
        CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
        carDetailsDTO.setVin(vin);
        return null;
    }

    static List<CarDetailsDTO> getDefaultCarDetailsList() {
        return new ArrayList<CarDetailsDTO>();
    }


}
