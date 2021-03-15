package com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.CarDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.CarEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface CarDTO2EntityConverter extends Converter<CarDTO, CarEntity> {
}
