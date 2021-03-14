package com.teenthofabud.codingchallenge.sharenow.polling.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.VehicleDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.VehicleEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface VehicleDTO2EntityConveter extends Converter<VehicleDTO, VehicleEntity> {
}
