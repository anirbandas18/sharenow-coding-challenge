package com.teenthofabud.codingchallenge.sharenow.polygon.repository;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolygonRepository extends MongoRepository<StrategicPolygonEntity, String> {
}
