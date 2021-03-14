package com.teenthofabud.codingchallenge.sharenow.vehicle.converter;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity.VehicleEntity;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.VehicleVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface VehicleEntity2VOConverter extends Converter<VehicleEntity, VehicleVO> {

    public VehicleVO convert(VehicleEntity entity);

}
