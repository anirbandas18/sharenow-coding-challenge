package com.teenthofabud.codingchallenge.sharenow.car.converter;

import com.teenthofabud.codingchallenge.sharenow.car.model.entity.PositionEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.PositionVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface PositionEntity2VOConverter extends Converter<PositionEntity, PositionVO> {

    public PositionVO convert(PositionEntity entity);

}
