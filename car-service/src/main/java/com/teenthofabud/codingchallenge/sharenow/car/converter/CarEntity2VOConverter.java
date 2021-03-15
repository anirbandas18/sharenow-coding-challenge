package com.teenthofabud.codingchallenge.sharenow.car.converter;

import com.teenthofabud.codingchallenge.sharenow.car.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface CarEntity2VOConverter extends Converter<CarEntity, CarVO> {

    public CarVO convert(CarEntity entity);

}
