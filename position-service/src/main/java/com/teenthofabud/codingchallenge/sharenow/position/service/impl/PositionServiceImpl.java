package com.teenthofabud.codingchallenge.sharenow.position.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.CarDetailsDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.car.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.GeoFeatureDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon.StrategicPolygonDetailedDTO;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionErrorCode;
import com.teenthofabud.codingchallenge.sharenow.position.model.error.PositionServiceException;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.*;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.Car2StrategicPolygonPositioningVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.CarMappedVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.PositionVO;
import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.*;
import com.teenthofabud.codingchallenge.sharenow.position.repository.CarServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.repository.PolygonServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.service.PlacementService;
import com.teenthofabud.codingchallenge.sharenow.position.service.PositionService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PositionServiceImpl implements PositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Autowired
    private PlacementService placementService;

    @Autowired
    private CarServiceClient carClient;

    @Autowired
    private PolygonServiceClient polygonClient;

    private Converter<CarDetailsDTO, CarMappedVO> carDetailsDTO2VOConverter;

    private Converter<StrategicPolygonDetailedDTO, StrategicPolygonMappedVO> polygonDetailsDTO2VOConverter;

    private Converter<PositionDTO, PositionVO> positionDTO2VOConverter;

    private Converter<GeoFeatureDTO, GeoFeatureVO> geoFeatureDTO2VOConverter;

    @PostConstruct
    private void init() {
        this.positionDTO2VOConverter = (dto) -> {
            PositionVO vo = new PositionVO();
            vo.setLatitude(dto.getLatitude());
            vo.setLongitude(dto.getLongitude());
            return vo;
        };
        this.carDetailsDTO2VOConverter = (dto) -> {
            CarMappedVO vo = new CarMappedVO();
            vo.setModel(dto.getModel());
            vo.setNumberPlate(dto.getNumberPlate());
            vo.setVin(dto.getVin());
            vo.setPosition(this.positionDTO2VOConverter.convert(dto.getPosition()));
            return vo;
        };
        this.geoFeatureDTO2VOConverter = (entity) -> {
            GeoFeatureVO vo = new GeoFeatureVO();
            if(entity != null) {
                vo.setGeometry(entity.getGeometry());
                vo.setName(entity.getName());
            }
            return vo;
        };
        this.polygonDetailsDTO2VOConverter = (dto) -> {
            StrategicPolygonMappedVO vo = new StrategicPolygonMappedVO();
            vo.setCityId(dto.getCityId());
            vo.setGeoFeatures(dto.getGeoFeatures().stream()
                    .map(gf -> this.geoFeatureDTO2VOConverter.convert(gf)).collect(Collectors.toList()));
            vo.setId(dto.getId());
            vo.setType(dto.getType());
            vo.setName(dto.getName());
            return vo;
        };
    }

    private PositionServiceException parseFeignErrorResponse(FeignException e) {
        LOGGER.error("Error requesting external service {}", e.getMessage());
        e.printStackTrace();
        Optional<ByteBuffer> optResponse = e.responseBody();
        String response = "";
        if(optResponse.isPresent()) {
            response = StandardCharsets.UTF_8.decode(optResponse.get()).toString();
            ObjectMapper om = new ObjectMapper();
            try {
                ErrorVO errVO = om.readValue(response, ErrorVO.class);
                response = errVO.getErrorMessage();
            } catch (JsonProcessingException jsonProcessingException) {
                LOGGER.error("Error parsing feign response: {}", jsonProcessingException.getMessage());
                jsonProcessingException.printStackTrace();
            }
        }
        return new PositionServiceException(e.getMessage(), PositionErrorCode.SYSTEM_ERROR, new Object[] {response});
    }

    @Override
    public Car2StrategicPolygonPositioningVO retrievePositionOfCarAndItsEnclosingPolygonByVin(String vin) throws PositionServiceException {
        try {
            Car2StrategicPolygonPositioningVO posVO = new Car2StrategicPolygonPositioningVO();
            boolean found = false;
            CarDetailsDTO carDTO = this.carClient.getCarDetailsByVin(vin);
            LOGGER.info("Retrieved car details for vin: {}", vin);
            List<StrategicPolygonDetailedDTO> polygonDTOList = this.polygonClient.getAllPolygons();
            if(polygonDTOList != null && !polygonDTOList.isEmpty()) {
                LOGGER.info("Retrieved strategic polygons: {}", polygonDTOList.size());
                for(StrategicPolygonDTO litePolygonDTO : polygonDTOList) {
                    StrategicPolygonDetailedDTO detailedPolygonDTO = this.polygonClient.getPolygonDetailsById(litePolygonDTO.getId());
                    if(this.placementService.isCarInsidePolygonConsiderOnlyOuterRing(carDTO, detailedPolygonDTO)) {
                        CarMappedVO carVO = this.carDetailsDTO2VOConverter.convert(carDTO);
                        StrategicPolygonMappedVO polygonVO = this.polygonDetailsDTO2VOConverter.convert(detailedPolygonDTO);
                        posVO.setCar(carVO);
                        posVO.setPolygon(polygonVO);
                        found = true;
                        LOGGER.info("Car with vin {} is present inside polygon with id {}", vin, detailedPolygonDTO.getId());
                        break;
                    }
                }
                if(found) {
                    return posVO;
                } else {
                    LOGGER.warn("Car with vin {} is not present in any polygon", vin);
                    throw new PositionServiceException("Unplaced car", PositionErrorCode.NOT_FOUND, new Object[] {"vin", vin});
                }
            } else {
                throw new PositionServiceException("No polygons found", PositionErrorCode.NOT_FOUND, new Object[] {"polygons", ""});
            }
        } catch (FeignException e) {
            throw this.parseFeignErrorResponse(e);
        }
    }

    @Override
    public StrategicPolygon2CarPositioningVO retrievePositionsOfCarsByPolygonById(String polygonId) throws PositionServiceException {
        try {
            StrategicPolygon2CarPositioningVO posVO = new StrategicPolygon2CarPositioningVO();
            StrategicPolygonDetailedDTO polygonDTO = this.polygonClient.getPolygonDetailsById(polygonId);
            LOGGER.info("Retrieved strategic polygon details for id: {}", polygonId);
            List<CarDetailsDTO> carDetailsDTOList = this.carClient.getAllCarsWithDetails();
            if(carDetailsDTOList != null && !carDetailsDTOList.isEmpty()) {
                LOGGER.info("Retrieved cars: {}", carDetailsDTOList.size());
                for(CarDetailsDTO carDetailsDTO : carDetailsDTOList) {
                    if(this.placementService.isCarInsidePolygonConsiderOnlyOuterRing(carDetailsDTO, polygonDTO)) {
                        CarMappedVO carVO = this.carDetailsDTO2VOConverter.convert(carDetailsDTO);
                        posVO.addCar(carVO);
                    }
                }
                if(posVO.hasCars()) {
                    StrategicPolygonMappedVO polygonVO = this.polygonDetailsDTO2VOConverter.convert(polygonDTO);
                    posVO.setPolygon(polygonVO);
                    LOGGER.info("Added {} cars to polygon with id {}", posVO.getCars().size(), polygonId);
                    return posVO;
                } else {
                    LOGGER.error("No cars could be placed in polygon with id {}", polygonId);
                    throw new PositionServiceException("No cars available", PositionErrorCode.NOT_FOUND, new Object[] {"cars", "are"});
                }
            } else {
                throw new PositionServiceException("No cars available", PositionErrorCode.NOT_FOUND, new Object[] {"cars", "are"});
            }
        } catch (FeignException e) {
            throw this.parseFeignErrorResponse(e);
        }
    }

}
