package com.teenthofabud.codingchallenge.sharenow.polygon.service;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonDetailedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PolygonService {

    public List<StrategicPolygonVO> retrieveAll();

    public StrategicPolygonDetailedVO retrieveById(String id);

    public StrategicPolygonDetailedVO retrieveByName(String name);

    public StrategicPolygonDetailedVO retrieveByCityId(String cityId);

    public StrategicPolygonDetailedVO retrieveByLegacyId(String legacyId);

    public List<StrategicPolygonVO> retrieveByType(String type);

}
