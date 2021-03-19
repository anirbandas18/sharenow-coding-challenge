package com.teenthofabud.codingchallenge.sharenow.polygon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.*;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonErrorCode;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.*;
import com.teenthofabud.codingchallenge.sharenow.polygon.repository.PolygonRepository;
import com.teenthofabud.codingchallenge.sharenow.polygon.service.impl.PolygonServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
public class PolygonServiceTests {

    private static List<StrategicPolygonEntity> LIST_OF_STRATEGIC_POLYGON_ENTITIES;

    private static StrategicPolygonEntity HAUPTBAHNHOF_AREA_ENTITY;
    private static StrategicPolygonEntity KRANKENHAUS_AREA_ENTITY;

    private static StrategicPolygonDetailedVO HAUPTBAHNHOF_AREA_VO;
    private static StrategicPolygonDetailedVO KRANKENHAUS_AREA_VO;

    private static String ASSUMED_HAUPTBAHNHOF_ID = "58a58bf685979b5415f3a39a";
    private static String ASSUMED_KRANKEDHAUS_ID = "58a58bf6766d51540f779930";

    private PolygonRepository repository;

    @InjectMocks
    private PolygonServiceImpl service = new PolygonServiceImpl();


    @BeforeAll
    private static void setUp() {
        String staticStrategicGeoJsonDumpFileName = "strategic-geojson-polygon-static-data.json";
        try {
            ObjectMapper om = new ObjectMapper();
            Resource staticStrategicGeoJsonDump = new ClassPathResource(staticStrategicGeoJsonDumpFileName);
            InputStream rawJson = staticStrategicGeoJsonDump.getInputStream();
            LIST_OF_STRATEGIC_POLYGON_ENTITIES = om.readValue(rawJson, new TypeReference<List<StrategicPolygonEntity>>() {});
            HAUPTBAHNHOF_AREA_ENTITY = LIST_OF_STRATEGIC_POLYGON_ENTITIES.stream().filter(spe -> spe.getId().compareTo(ASSUMED_HAUPTBAHNHOF_ID) == 0).findFirst().get();
            KRANKENHAUS_AREA_ENTITY = LIST_OF_STRATEGIC_POLYGON_ENTITIES.stream().filter(spe -> spe.getId().compareTo(ASSUMED_KRANKEDHAUS_ID) == 0).findFirst().get();
            if(HAUPTBAHNHOF_AREA_ENTITY != null) {
                HAUPTBAHNHOF_AREA_VO = new StrategicPolygonDetailedVO();
                HAUPTBAHNHOF_AREA_VO.setCityId(HAUPTBAHNHOF_AREA_ENTITY.getCityId());
                HAUPTBAHNHOF_AREA_VO.setCreatedAt(HAUPTBAHNHOF_AREA_ENTITY.getCreatedAt());
                HAUPTBAHNHOF_AREA_VO.setGeometry(HAUPTBAHNHOF_AREA_ENTITY.getGeometry());
                HAUPTBAHNHOF_AREA_VO.setId(HAUPTBAHNHOF_AREA_ENTITY.getId());
                HAUPTBAHNHOF_AREA_VO.setLegacyId(HAUPTBAHNHOF_AREA_ENTITY.getLegacyId());
                HAUPTBAHNHOF_AREA_VO.setName(HAUPTBAHNHOF_AREA_ENTITY.getName());
                HAUPTBAHNHOF_AREA_VO.setType(HAUPTBAHNHOF_AREA_ENTITY.getType());
                HAUPTBAHNHOF_AREA_VO.setComputed(computedConverter(activeTimedOptionsConverter()).convert(HAUPTBAHNHOF_AREA_ENTITY.getComputed()));
                HAUPTBAHNHOF_AREA_VO.setOptions(optionsConverter().convert(HAUPTBAHNHOF_AREA_ENTITY.getOptions()));
                HAUPTBAHNHOF_AREA_VO.setV(HAUPTBAHNHOF_AREA_ENTITY.getV());
                HAUPTBAHNHOF_AREA_VO.setVersion(HAUPTBAHNHOF_AREA_ENTITY.getVersion());
                HAUPTBAHNHOF_AREA_VO.setGeoFeatures(HAUPTBAHNHOF_AREA_ENTITY.getGeoFeatures().stream().map(gf -> geoFeatureConverter().convert(gf)).collect(Collectors.toList()));
                HAUPTBAHNHOF_AREA_VO.setTimedOptions(HAUPTBAHNHOF_AREA_ENTITY.getTimedOptions().stream().map(to -> timedOptionsConverter().convert(to)).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("No strategic polygon with id " + ASSUMED_HAUPTBAHNHOF_ID + " is available as test data");
            }
            if(KRANKENHAUS_AREA_ENTITY != null) {
                KRANKENHAUS_AREA_VO = new StrategicPolygonDetailedVO();
                KRANKENHAUS_AREA_VO.setCityId(KRANKENHAUS_AREA_ENTITY.getCityId());
                KRANKENHAUS_AREA_VO.setCreatedAt(KRANKENHAUS_AREA_ENTITY.getCreatedAt());
                KRANKENHAUS_AREA_VO.setGeometry(KRANKENHAUS_AREA_ENTITY.getGeometry());
                KRANKENHAUS_AREA_VO.setId(KRANKENHAUS_AREA_ENTITY.getId());
                KRANKENHAUS_AREA_VO.setLegacyId(KRANKENHAUS_AREA_ENTITY.getLegacyId());
                KRANKENHAUS_AREA_VO.setName(KRANKENHAUS_AREA_ENTITY.getName());
                KRANKENHAUS_AREA_VO.setType(KRANKENHAUS_AREA_ENTITY.getType());
                KRANKENHAUS_AREA_VO.setComputed(computedConverter(activeTimedOptionsConverter()).convert(KRANKENHAUS_AREA_ENTITY.getComputed()));
                KRANKENHAUS_AREA_VO.setOptions(optionsConverter().convert(KRANKENHAUS_AREA_ENTITY.getOptions()));
                KRANKENHAUS_AREA_VO.setV(KRANKENHAUS_AREA_ENTITY.getV());
                KRANKENHAUS_AREA_VO.setVersion(KRANKENHAUS_AREA_ENTITY.getVersion());
                KRANKENHAUS_AREA_VO.setGeoFeatures(KRANKENHAUS_AREA_ENTITY.getGeoFeatures().stream().map(gf -> geoFeatureConverter().convert(gf)).collect(Collectors.toList()));
                KRANKENHAUS_AREA_VO.setTimedOptions(KRANKENHAUS_AREA_ENTITY.getTimedOptions().stream().map(to -> timedOptionsConverter().convert(to)).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("No strategic polygon with id " + ASSUMED_KRANKEDHAUS_ID + " is available as test data");
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private static Converter<ActiveTimedOptionsEntity, ActiveTimedOptionsVO> activeTimedOptionsConverter() {
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

    private  static Converter<ComputedEntity, ComputedVO> computedConverter(Converter<ActiveTimedOptionsEntity, ActiveTimedOptionsVO> activeTimedOptionsConverter) {
        return (entity) -> {
            ComputedVO vo = new ComputedVO();
            if(entity != null) {
                vo.setActiveTimedOptions(activeTimedOptionsConverter.convert(entity.getActiveTimedOptions()));
            }
            return vo;
        };
    }

    private  static Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter() {
        return (entity) -> {
            GeoFeatureVO vo = new GeoFeatureVO();
            if(entity != null) {
                vo.setGeometry(entity.getGeometry()); // TODO: look into this
                vo.setName(entity.getName());
            }
            return vo;
        };
    }

    private  static Converter<OptionsEntity, OptionsVO> optionsConverter() {
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

    private  static Converter<TimedOptionsEntity, TimedOptionsVO> timedOptionsConverter() {
        return (entity) -> {
            TimedOptionsVO vo = new TimedOptionsVO();
            if(entity != null) {
                vo.setChangesOverTime(entity.getChangesOverTime()); // TODO: look into this
                vo.setKey(entity.getKey());
            }
            return vo;
        };
    }

    private  static Converter<StrategicPolygonEntity, StrategicPolygonDetailedVO> strategicPolygonComplexConverter(Converter<ComputedEntity, ComputedVO> computedConverter,
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

    private  static Converter<StrategicPolygonEntity, StrategicPolygonVO> strategicPolygonSimpleConverter(Converter<GeoFeatureEntity, GeoFeatureVO> geoFeatureConverter) {
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

    @BeforeEach
    private void init() {
        this.service.init();
        this.repository = Mockito.mock(PolygonRepository.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test retrieval all strategic polygons that are available")
    void testRetrievalOf_AllStrategicPolygons_ThatAreAvailable() {
        // arrange
        when(repository.findAll()).thenReturn(LIST_OF_STRATEGIC_POLYGON_ENTITIES);

        // act
        List<StrategicPolygonDetailedVO> listOfPolygonVOs = service.retrieveAll();

        // assert
        Assert.assertNotNull(listOfPolygonVOs);
        Assert.assertEquals(LIST_OF_STRATEGIC_POLYGON_ENTITIES.size(), listOfPolygonVOs.size());
    }

    @Test
    @DisplayName("Test retrieval failure when no cars are available")
    void testRetrievalFailure_WhenNoStrategicPolygons_AreAvailable() {
        // arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // act
        List<StrategicPolygonDetailedVO> listOfPolygonVOs = service.retrieveAll();

        // assert
        Assert.assertNotNull(listOfPolygonVOs);
        Assert.assertEquals(0, listOfPolygonVOs.size());
    }

    @Test
    @DisplayName("Test throw exception when id of strategic polygon is not available")
    void testThrowException_WhenIdOfStrategicPolygon_IsNotAvailable() {
        // arrange
        String polygonId = "123";
        when(repository.findById(polygonId)).thenReturn(Optional.empty());
        StrategicPolygonDetailedVO polygonVO = null;
        PolygonServiceException psex = null;
        PolygonErrorCode errorCode = PolygonErrorCode.NOT_FOUND;

        // act
        try {
            polygonVO = service.retrieveById(polygonId);
        } catch (PolygonServiceException e) {
            psex = e;
        }

        // assert
        Assert.assertNull(polygonVO);
        Assert.assertNotNull(psex);
        Assert.assertEquals("No polygon found that matches with id", psex.getMessage());
        Assert.assertEquals(errorCode.getErrorCode(), psex.getError().getErrorCode());
        Assert.assertEquals(errorCode.getStatusCode(), psex.getError().getStatusCode());
    }

    @Test
    @DisplayName("Test throw exception when id of strategic polygon is is invalid")
    void testThrowException_WhenIdOfStrategicPolygon_IsInvalid() {
        // arrange
        when(repository.findById("")).thenReturn(Optional.empty());
        StrategicPolygonDetailedVO polygonVO = null;
        PolygonServiceException psex = null;
        PolygonErrorCode errorCode = PolygonErrorCode.INVALID_PARAMETER;

        // act
        try {
            polygonVO = service.retrieveById("");
        } catch (PolygonServiceException e) {
            psex = e;
        }

        // assert
        Assert.assertNull(polygonVO);
        Assert.assertNotNull(psex);
        Assert.assertEquals("Invalid id", psex.getMessage());
        Assert.assertEquals(errorCode.getErrorCode(), psex.getError().getErrorCode());
        Assert.assertEquals(errorCode.getStatusCode(), psex.getError().getStatusCode());
    }

    @Test
    @DisplayName("Test retrieval of strategic polygon details of by its id")
    void testRetrievalOf_StrategicPolygonDetails_ByItsId() {
        // arrange
        StrategicPolygonEntity searchEntity = new StrategicPolygonEntity();
        searchEntity.setCityId(HAUPTBAHNHOF_AREA_ENTITY.getCityId());
        searchEntity.setCreatedAt(HAUPTBAHNHOF_AREA_ENTITY.getCreatedAt());
        searchEntity.setUpdatedAt(HAUPTBAHNHOF_AREA_ENTITY.getUpdatedAt());
        searchEntity.setGeometry(HAUPTBAHNHOF_AREA_ENTITY.getGeometry());
        searchEntity.setId(HAUPTBAHNHOF_AREA_ENTITY.getId());
        searchEntity.setLegacyId(HAUPTBAHNHOF_AREA_ENTITY.getLegacyId());
        searchEntity.setName(HAUPTBAHNHOF_AREA_ENTITY.getName());
        searchEntity.setType(HAUPTBAHNHOF_AREA_ENTITY.getType());
        searchEntity.setComputed(HAUPTBAHNHOF_AREA_ENTITY.getComputed());
        searchEntity.setOptions(HAUPTBAHNHOF_AREA_ENTITY.getOptions());
        searchEntity.setV(HAUPTBAHNHOF_AREA_ENTITY.getV());
        searchEntity.setVersion(HAUPTBAHNHOF_AREA_ENTITY.getVersion());
        searchEntity.setGeoFeatures(HAUPTBAHNHOF_AREA_ENTITY.getGeoFeatures());
        searchEntity.setTimedOptions(HAUPTBAHNHOF_AREA_ENTITY.getTimedOptions());
        String id = ASSUMED_HAUPTBAHNHOF_ID;
        when(repository.findById(id)).thenReturn(Optional.of(searchEntity));
        StrategicPolygonDetailedVO polygonVO = null;

        // act
        try {
            polygonVO = service.retrieveById(id);
        } catch (PolygonServiceException e) {
            // consume exception here because we know that this data will not cause any exception
        }

        // assert - ONLY first level properties for simplicity
        Assert.assertNotNull(polygonVO);
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getId(), polygonVO.getId());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getCityId(), polygonVO.getCityId());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getCreatedAt(), polygonVO.getCreatedAt());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getLegacyId(), polygonVO.getLegacyId());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getName(), polygonVO.getName());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getType(), polygonVO.getType());
        Assert.assertEquals(HAUPTBAHNHOF_AREA_ENTITY.getUpdatedAt(), polygonVO.getUpdatedAt());
        Assert.assertEquals(String.valueOf(HAUPTBAHNHOF_AREA_ENTITY.getV()), String.valueOf(polygonVO.getV()));
        Assert.assertEquals(String.valueOf(HAUPTBAHNHOF_AREA_ENTITY.getVersion()), String.valueOf(polygonVO.getVersion()));
    }

}
