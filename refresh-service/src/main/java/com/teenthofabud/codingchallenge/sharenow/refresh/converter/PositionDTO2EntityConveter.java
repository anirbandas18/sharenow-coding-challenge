package com.teenthofabud.codingchallenge.sharenow.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.refresh.model.dto.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.refresh.model.entity.PositionEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface PositionDTO2EntityConveter extends Converter<PositionDTO, PositionEntity> {
}
