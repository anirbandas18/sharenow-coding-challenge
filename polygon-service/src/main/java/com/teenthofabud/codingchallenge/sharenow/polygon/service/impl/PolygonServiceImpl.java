package com.teenthofabud.codingchallenge.sharenow.polygon.service.impl;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ActiveTimedOptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ComputedEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.GeoFeatureEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.OptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.TimedOptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonErrorCode;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.ActiveTimedOptionsVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.ComputedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.GeoFeatureVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.OptionsVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonDetailedVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.StrategicPolygonVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.TimedOptionsVO;
import com.teenthofabud.codingchallenge.sharenow.polygon.repository.PolygonRepository;
import com.teenthofabud.codingchallenge.sharenow.polygon.service.PolygonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PolygonServiceImpl implements PolygonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonServiceImpl.class);

    @Autowired
    private PolygonRepository repository;

    private Converter<ActiveTimedOptionsEntity, ActiveTimedOptionsVO> activeTimedOptionsConverter;

    private Converter<ComputedEntity, ComputedVO> computedConverter;

    private Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter;

    private Converter<OptionsEntity, OptionsVO> optionsConverter;

    private Converter<StrategicPolygonEntity, StrategicPolygonVO> strategicPolygonSimpleConverter;

    private Converter<StrategicPolygonEntity, StrategicPolygonDetailedVO> strategicPolygonComplexConverter;

    private Converter<TimedOptionsEntity, TimedOptionsVO> timedOptionsConverter;


    @PostConstruct
    private void init() {
        this.activeTimedOptionsConverter = activeTimedOptionsConverter();
        this.geoFeatureConverter = geoFeatureConverter();
        this.optionsConverter = optionsConverter();
        this.timedOptionsConverter = timedOptionsConverter();
        this.computedConverter = computedConverter(this.activeTimedOptionsConverter);
        this.strategicPolygonComplexConverter = strategicPolygonComplexConverter(this.computedConverter, this.geoFeatureConverter,
                this.optionsConverter, this.timedOptionsConverter);
        this.strategicPolygonSimpleConverter = strategicPolygonSimpleConverter(this.geoFeatureConverter);
    }

    private Converter<ActiveTimedOptionsEntity, ActiveTimedOptionsVO> activeTimedOptionsConverter() {
        return (entity) -> {
            ActiveTimedOptionsVO vo = new ActiveTimedOptionsVO();
            if(entity != null) {
                vo.setIdleTime(entity.getIdleTime());
                vo.setMax(entity.getMax());
                vo.setMin(entity.getMin());
                vo.setRevenue(entity.getRevenue());
                vo.setWalkingRange1(entity.getWalkingRange1());
                vo.setWalkingRange2(entity.getWalkingRange2());
            }
            return vo;
        };
    }

    private Converter<ComputedEntity, ComputedVO> computedConverter(Converter<ActiveTimedOptionsEntity, ActiveTimedOptionsVO> activeTimedOptionsConverter) {
        return (entity) -> {
            ComputedVO vo = new ComputedVO();
            if(entity != null) {
                vo.setActiveTimedOptions(activeTimedOptionsConverter.convert(entity.getActiveTimedOptions()));
            }
            return vo;
        };
    }

    private Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter() {
        return (entity) -> {
            GeoFeatureVO vo = new GeoFeatureVO();
            if(entity != null) {
                vo.setGeometry(entity.getGeometry()); // TODO: look into this
                vo.setName(entity.getName());
            }
            return vo;
        };
    }

    private Converter<OptionsEntity, OptionsVO> optionsConverter() {
        return (entity) -> {
            OptionsVO vo = new OptionsVO();
            if(entity != null) {
                vo.setActive(entity.isActive());
                vo.setArea(entity.getArea());
                vo.setExcluded(entity.isExcluded());
            }
            return vo;
        };
    }

    private Converter<TimedOptionsEntity, TimedOptionsVO> timedOptionsConverter() {
        return (entity) -> {
            TimedOptionsVO vo = new TimedOptionsVO();
            if(entity != null) {
                vo.setChangesOverTime(entity.getChangesOverTime()); // TODO: look into this
                vo.setKey(entity.getKey());
            }
            return vo;
        };
    }

    private Converter<StrategicPolygonEntity, StrategicPolygonDetailedVO> strategicPolygonComplexConverter(Converter<ComputedEntity, ComputedVO> computedConverter,
                                                                                             Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter,
                                                                                             Converter<OptionsEntity, OptionsVO> optionsConverter,
                                                                                             Converter<TimedOptionsEntity, TimedOptionsVO> timedOptionsConverter) {
        return (entity) -> {
            StrategicPolygonDetailedVO vo = new StrategicPolygonDetailedVO();
            if(entity != null) {
                vo.setCityId(entity.getCityId());
                vo.setComputed(computedConverter.convert(entity.getComputed()));
                vo.setCreatedAt(entity.getCreatedAt());
                vo.setId(entity.getId());
                vo.setGeometry(entity.getGeometry());
                vo.setLegacyId(entity.getLegacyId());
                vo.setName(entity.getName());
                vo.setType(entity.getType());
                vo.setUpdatedAt(entity.getUpdatedAt());
                vo.setV(entity.getV());
                vo.setVersion(entity.getVersion());
                vo.setGeoFeatures(entity.getGeoFeatures().stream()
                        .map(gf -> geoFeatureConverter.convert(gf)).collect(Collectors.toList()));
                vo.setOptions(optionsConverter.convert(entity.getOptions()));
                vo.setTimedOptions(entity.getTimedOptions().stream()
                        .map(to -> timedOptionsConverter.convert(to)).collect(Collectors.toList()));
            }
            return vo;
        };
    }

    private Converter<StrategicPolygonEntity, StrategicPolygonVO> strategicPolygonSimpleConverter(Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter) {
        return (entity) -> {
            StrategicPolygonVO vo = new StrategicPolygonVO();
            if(entity != null) {
                vo.setCityId(entity.getCityId());
                vo.setCreatedAt(entity.getCreatedAt());
                vo.setId(entity.getId());
                vo.setLegacyId(entity.getLegacyId());
                vo.setName(entity.getName());
                vo.setType(entity.getType());
                vo.setUpdatedAt(entity.getUpdatedAt());
                vo.setV(entity.getV());
                vo.setVersion(entity.getVersion());
                vo.setGeoFeatures(entity.getGeoFeatures().stream()
                        .map(gf -> geoFeatureConverter.convert(gf)).collect(Collectors.toList()));
            }
            return vo;
        };
    }


    @Override
    public List<StrategicPolygonVO> retrieveAll() {
        List<StrategicPolygonVO> voList = new ArrayList<>();
        List<StrategicPolygonEntity> entityList = this.repository.findAll();
        for(StrategicPolygonEntity entity : entityList) {
            StrategicPolygonVO vo = this.strategicPolygonSimpleConverter.convert(entity);
            voList.add(vo);
        }
        LOGGER.info("Found {} polygons", voList.size());
        return voList;
    }

    @Override
    public StrategicPolygonDetailedVO retrieveById(String id) throws PolygonServiceException {
        if(StringUtils.hasText(id)) {
            Optional<StrategicPolygonEntity> optEntity = this.repository.findById(id);
            if(optEntity.isPresent()) {
                StrategicPolygonEntity entity = optEntity.get();
                StrategicPolygonDetailedVO vo = this.strategicPolygonComplexConverter.convert(entity);
                LOGGER.info("Found polygon with id: {}", id);
                return vo;
            } else {
                LOGGER.error("No polygon found with id: {}", id);
                throw new PolygonServiceException("No polygon found that matches with id", PolygonErrorCode.NOT_FOUND, new Object[] {"id", id});
            }
        } else {
            LOGGER.error("Invalid id: {}", id);
            throw new PolygonServiceException("Invalid id", PolygonErrorCode.INVALID_PARAMETER, new Object[] {"id"});
        }
    }

    @Override
    public StrategicPolygonDetailedVO retrieveByLegacyId(String legacyId) throws PolygonServiceException {
        if(StringUtils.hasText(legacyId)) {
            StrategicPolygonEntity entity = this.repository.findByLegacyId(legacyId);
            if(entity != null) {
                StrategicPolygonDetailedVO vo = this.strategicPolygonComplexConverter.convert(entity);
                LOGGER.info("Found polygon with legacyId: {}", legacyId);
                return vo;
            } else {
                LOGGER.error("No polygon found with legacyId: {}", legacyId);
                throw new PolygonServiceException("No polygon found that matches with legacyId", PolygonErrorCode.NOT_FOUND, new Object[] {"legacyId", legacyId});
            }
        } else {
            LOGGER.error("Invalid legacyId: {}", legacyId);
            throw new PolygonServiceException("Invalid legacyId", PolygonErrorCode.INVALID_PARAMETER, new Object[] {"legacyId"});
        }
    }

    @Override
    public List<StrategicPolygonDetailedVO> retrieveByType(String type) throws PolygonServiceException {
        if(StringUtils.hasText(type)) {
            List<StrategicPolygonDetailedVO> voList = new ArrayList<>();
            List<StrategicPolygonEntity> entityList = this.repository.findAllByType(type);
            for(StrategicPolygonEntity entity : entityList) {
                StrategicPolygonDetailedVO vo = this.strategicPolygonComplexConverter.convert(entity);
                voList.add(vo);
            }
            LOGGER.info("Found {} polygons of type: {}", voList.size(), type);
            return voList;
        } else {
            LOGGER.error("Invalid type: {}", type);
            throw new PolygonServiceException("Invalid type", PolygonErrorCode.INVALID_PARAMETER, new Object[] {"type"});
        }
    }

    @Override
    public List<StrategicPolygonDetailedVO> retrieveByCityId(String cityId) throws PolygonServiceException {
        if(StringUtils.hasText(cityId)) {
            List<StrategicPolygonDetailedVO> voList = new ArrayList<>();
            List<StrategicPolygonEntity> entityList = this.repository.findAllByCityId(cityId);
            for(StrategicPolygonEntity entity : entityList) {
                StrategicPolygonDetailedVO vo = this.strategicPolygonComplexConverter.convert(entity);
                voList.add(vo);
            }
            LOGGER.info("Found {} polygons of cityId: {}", voList.size(), cityId);
            return voList;
        } else {
            LOGGER.error("Invalid cityId: {}", cityId);
            throw new PolygonServiceException("Invalid cityId", PolygonErrorCode.INVALID_PARAMETER, new Object[] {"cityId"});
        }
    }

    @Override
    public List<StrategicPolygonDetailedVO> retrieveByName(String name) throws PolygonServiceException {
        if(StringUtils.hasText(name)) {
            List<StrategicPolygonDetailedVO> voList = new ArrayList<>();
            List<StrategicPolygonEntity> entityList = this.repository.findAllByName(name);
            for(StrategicPolygonEntity entity : entityList) {
                StrategicPolygonDetailedVO vo = this.strategicPolygonComplexConverter.convert(entity);
                voList.add(vo);
            }
            LOGGER.info("Found {} polygons of name: {}", voList.size(), name);
            return voList;
        } else {
            LOGGER.error("Invalid name: {}", name);
            throw new PolygonServiceException("Invalid name", PolygonErrorCode.INVALID_PARAMETER, new Object[] {"name"});
        }
    }

}
