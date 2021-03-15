package com.teenthofabud.codingchallenge.sharenow.car.repository;

import com.teenthofabud.codingchallenge.sharenow.car.model.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, String> {

    public List<CarEntity> findByVin(String vin);

}
