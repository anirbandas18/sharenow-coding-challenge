package com.teenthofabud.codingchallenge.sharenow.vehicle.converter;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity.PositionEntity;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.PositionVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface PositionEntity2VOConverter extends Converter<PositionEntity, PositionVO> {

    public PositionVO convert(PositionEntity entity);

}
