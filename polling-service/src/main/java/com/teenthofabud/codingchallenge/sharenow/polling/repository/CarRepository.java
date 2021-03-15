package com.teenthofabud.codingchallenge.sharenow.polling.repository;

import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, String> {
}
