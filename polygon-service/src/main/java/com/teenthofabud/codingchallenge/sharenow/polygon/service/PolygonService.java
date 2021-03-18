package com.teenthofabud.codingchallenge.sharenow.polygon.service;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonDetailedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PolygonService {

    public List<StrategicPolygonDetailedVO> retrieveAll();

    public StrategicPolygonDetailedVO retrieveById(String id) throws PolygonServiceException;

    public StrategicPolygonDetailedVO retrieveByLegacyId(String legacyId) throws PolygonServiceException;

    public List<StrategicPolygonDetailedVO> retrieveByName(String name) throws PolygonServiceException;

    public List<StrategicPolygonDetailedVO> retrieveByType(String type) throws PolygonServiceException;

    public List<StrategicPolygonDetailedVO> retrieveByCityId(String cityId) throws PolygonServiceException;

}
