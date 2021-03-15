package com.teenthofabud.codingchallenge.sharenow.polygon.filter;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.ActiveTimedOptionsDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.ComputedDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.GeoFeatureDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.OptionsDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.StrategicPolygonDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.dto.TimedOptionsDTO;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ActiveTimedOptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.ComputedEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.GeoFeatureEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.OptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.TimedOptionsEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.repository.PolygonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeedDataHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedDataHandler.class);

    @Value("${polygon.dump.stuttgart.url}")
    private String polygonDataDumpURLForStuttgart;

    @Autowired
    private RestClientErrorHandler errorHandler;

    @Autowired
    private PolygonRepository repository;

    private RestTemplate restClient;

    private Converter<ActiveTimedOptionsDTO, ActiveTimedOptionsEntity> activeTimedOptionsConverter;

    private Converter<ComputedDTO, ComputedEntity> computedConverter;

    private Converter<GeoFeatureDTO, GeoFeatureEntity> geoFeatureConverter;

    private Converter<OptionsDTO, OptionsEntity> optionsConverter;

    private Converter<StrategicPolygonDTO, StrategicPolygonEntity> strategicPolygonConverter;

    private Converter<TimedOptionsDTO, TimedOptionsEntity> timedOptionsConverter;

    @PostConstruct
    private void init() {
        this.restClient = restTemplate();
        this.activeTimedOptionsConverter = activeTimedOptionsConverter();
        this.geoFeatureConverter = geoFeatureConverter();
        this.optionsConverter = optionsConverter();
        this.timedOptionsConverter = timedOptionsConverter();
        this.computedConverter = computedConverter(this.activeTimedOptionsConverter);
        this.strategicPolygonConverter = strategicPolygonConverter(this.computedConverter, this.geoFeatureConverter,
                this.optionsConverter, this.timedOptionsConverter);
    }

    private RestTemplate restTemplate() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        return new RestTemplateBuilder().errorHandler(errorHandler).messageConverters(messageConverters).build();
    }

    private Converter<ActiveTimedOptionsDTO, ActiveTimedOptionsEntity> activeTimedOptionsConverter() {
        return (dto) -> {
            ActiveTimedOptionsEntity entity = new ActiveTimedOptionsEntity();
            if(dto != null) {
                entity.setIdleTime(dto.getIdleTime());
                entity.setMax(dto.getMax());
                entity.setMin(dto.getMin());
                entity.setRevenue(dto.getRevenue());
                entity.setWalkingRange1(dto.getWalkingRange1());
                entity.setWalkingRange2(dto.getWalkingRange2());
            }
            return entity;
        };
    }

    private Converter<ComputedDTO, ComputedEntity> computedConverter(Converter<ActiveTimedOptionsDTO, ActiveTimedOptionsEntity> activeTimedOptionsConverter) {
        return (dto) -> {
            ComputedEntity entity = new ComputedEntity();
            if(dto != null) {
                entity.setActiveTimedOptions(activeTimedOptionsConverter.convert(dto.getActiveTimedOptions()));
            }
            return entity;
        };
    }

    private Converter<GeoFeatureDTO, GeoFeatureEntity> geoFeatureConverter() {
        return (dto) -> {
            GeoFeatureEntity entity = new GeoFeatureEntity();
            if(dto != null) {
                entity.setGeometry(dto.getGeometry()); // TODO: look into this
                entity.setName(dto.getName());
            }
            return entity;
        };
    }

    private Converter<OptionsDTO, OptionsEntity> optionsConverter() {
        return (dto) -> {
            OptionsEntity entity = new OptionsEntity();
            if(dto != null) {
                entity.setActive(dto.isActive());
                entity.setArea(dto.getArea());
                entity.setExcluded(dto.isExcluded());
            }
            return entity;
        };
    }

    private Converter<TimedOptionsDTO, TimedOptionsEntity> timedOptionsConverter() {
        return (dto) -> {
            TimedOptionsEntity entity = new TimedOptionsEntity();
            if(dto != null) {
                entity.setChangesOverTime(dto.getChangesOverTime()); // TODO: look into this
                entity.setKey(dto.getKey());
            }
            return entity;
        };
    }

    private Converter<StrategicPolygonDTO, StrategicPolygonEntity> strategicPolygonConverter(Converter<ComputedDTO, ComputedEntity> computedConverter,
                                                                                             Converter<GeoFeatureDTO, GeoFeatureEntity> geoFeatureConverter,
                                                                                             Converter<OptionsDTO, OptionsEntity> optionsConverter,
                                                                                             Converter<TimedOptionsDTO, TimedOptionsEntity> timedOptionsConverter) {
        return (dto) -> {
            StrategicPolygonEntity entity = new StrategicPolygonEntity();
            if(dto != null) {
                entity.setCityId(dto.getCityId());
                entity.setComputed(computedConverter.convert(dto.getComputed()));
                entity.setCreatedAt(dto.getCreatedAt());
                entity.setId(dto.getId());
                entity.setGeometry(dto.getGeometry());
                entity.setLegacyId(dto.getLegacyId());
                entity.setName(dto.getName());
                entity.setType(dto.getType());
                entity.setUpdatedAt(dto.getUpdatedAt());
                entity.setV(dto.getV());
                entity.setVersion(dto.getVersion());
                entity.setGeoFeatures(dto.getGeoFeatures().stream()
                        .map(gf -> geoFeatureConverter.convert(gf)).collect(Collectors.toList()));
                entity.setOptions(optionsConverter.convert(dto.getOptions()));
                entity.setTimedOptions(dto.getTimedOptions().stream()
                        .map(to -> timedOptionsConverter.convert(to)).collect(Collectors.toList()));
            }
            return entity;
        };
    }


    /**
     * Capture the context refreshed event which denotes that all beans have been initialized and the application context is ready
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        try {
            List<StrategicPolygonDTO> dtoList = input();
            LOGGER.info("Received input size: ", dtoList.size());
            List<StrategicPolygonEntity> entityList = process(dtoList);
            int count = output(entityList);
            LOGGER.info("Seeding successful: {}", count == entityList.size());
        } catch (PolygonServiceException e) {
            e.printStackTrace();
        }
    }

    private List<StrategicPolygonDTO> input() throws PolygonServiceException {
        List<StrategicPolygonDTO> collection = new ArrayList<>();
        LOGGER.info("Requesting : {}", polygonDataDumpURLForStuttgart);
        ResponseEntity<List<StrategicPolygonDTO>> responseEntity =
                 this.restClient.exchange(polygonDataDumpURLForStuttgart, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StrategicPolygonDTO>>() {});
        collection = responseEntity.getBody();
        LOGGER.info("Received response: {}", collection != null);
        return collection;
    }

    private List<StrategicPolygonEntity> process(List<StrategicPolygonDTO> dtoList) {
        List<StrategicPolygonEntity> entityList = new ArrayList<>();
        if(dtoList != null && !dtoList.isEmpty()) {
            for(StrategicPolygonDTO dto : dtoList) {
                StrategicPolygonEntity entity = strategicPolygonConverter.convert(dto);
                entityList.add(entity);
            }
        }
        LOGGER.info("Converted {} dto items to entity items", entityList.size());
        return entityList;
    }

    private int output(List<StrategicPolygonEntity> collection) {
        int count = 0;
        for(StrategicPolygonEntity entity : collection) {
            if(!this.repository.existsById(entity.getId())) {
                StrategicPolygonEntity saved = this.repository.save(entity);
                if(saved != null) {
                    count++;
                    LOGGER.debug("Saved strategic polygon with id: {}", saved.getId());
                }
            } else {
                LOGGER.debug("Strategic polygon with id: {} exists", entity.getId());
            }
        }
        LOGGER.info("Saved {} strategic polygons", count);
        return count;
    }
}
