package com.teenthofabud.codingchallenge.sharenow.polygon.service.impl;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ActiveTimedOptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ComputedEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.GeoFeatureEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.OptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.TimedOptionsEntity;
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

import javax.annotation.PostConstruct;
import java.util.List;
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
        this.strategicPolygonSimpleConverter = strategicPolygonSimpleConverter();
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

    private Converter<StrategicPolygonEntity, StrategicPolygonVO> strategicPolygonSimpleConverter() {
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

            }
            return vo;
        };
    }


    @Override
    public List<StrategicPolygonVO> retrieveAll() {
        return null;
    }

    @Override
    public StrategicPolygonDetailedVO retrieveById(String id) {
        return null;
    }

    @Override
    public StrategicPolygonDetailedVO retrieveByName(String name) {
        return null;
    }

    @Override
    public StrategicPolygonDetailedVO retrieveByCityId(String cityId) {
        return null;
    }

    @Override
    public StrategicPolygonDetailedVO retrieveByLegacyId(String legacyId) {
        return null;
    }

    @Override
    public List<StrategicPolygonVO> retrieveByType(String type) {
        return null;
    }
}
