package com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.PositionEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface PositionDTO2EntityConverter extends Converter<PositionDTO, PositionEntity> {
}
