package com.teenthofabud.codingchallenge.sharenow.vehicle.repository;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, String> {

    public List<VehicleEntity> findByVin(String vin);

}
