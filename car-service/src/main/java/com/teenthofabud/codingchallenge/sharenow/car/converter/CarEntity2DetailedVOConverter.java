package com.teenthofabud.codingchallenge.sharenow.car.converter;

import com.teenthofabud.codingchallenge.sharenow.car.model.entity.CarEntity;
import com.teenthofabud.codingchallenge.sharenow.car.model.vo.CarDetailsVO;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface CarEntity2DetailedVOConverter extends Converter<CarEntity, CarDetailsVO> {

    public CarDetailsVO convert(CarEntity entity);

}
