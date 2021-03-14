package com.teenthofabud.codingchallenge.sharenow.polling.repository;

import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, String> {
}
