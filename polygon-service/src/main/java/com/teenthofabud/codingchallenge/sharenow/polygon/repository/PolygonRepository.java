package com.teenthofabud.codingchallenge.sharenow.polygon.repository;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolygonRepository extends MongoRepository<StrategicPolygonEntity, String> {

    public List<StrategicPolygonEntity> findAllByName(String name);

    public List<StrategicPolygonEntity> findAllByCityId(String cityId);

    public StrategicPolygonEntity findByLegacyId(String legacyId);

    public List<StrategicPolygonEntity> findAllByType(String type);

}
