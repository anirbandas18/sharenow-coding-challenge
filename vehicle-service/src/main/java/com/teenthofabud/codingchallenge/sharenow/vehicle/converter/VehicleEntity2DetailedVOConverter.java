package com.teenthofabud.codingchallenge.sharenow.vehicle.converter;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity.VehicleEntity;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleDetailsVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface VehicleEntity2DetailedVOConverter extends Converter<VehicleEntity, VehicleDetailsVO> {

    public VehicleDetailsVO convert(VehicleEntity entity);

}
