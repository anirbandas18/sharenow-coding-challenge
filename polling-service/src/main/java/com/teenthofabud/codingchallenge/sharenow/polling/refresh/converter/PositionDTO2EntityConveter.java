package com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.entity.PositionEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface PositionDTO2EntityConveter extends Converter<PositionDTO, PositionEntity> {
}
