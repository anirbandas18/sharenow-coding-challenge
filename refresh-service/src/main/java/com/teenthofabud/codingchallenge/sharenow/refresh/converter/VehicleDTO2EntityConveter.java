package com.teenthofabud.codingchallenge.sharenow.refresh.converter;

import com.teenthofabud.codingchallenge.sharenow.refresh.model.dto.VehicleDTO;
import com.teenthofabud.codingchallenge.sharenow.refresh.model.entity.VehicleEntity;
import org.springframework.core.convert.converter.Converter;

@FunctionalInterface
public interface VehicleDTO2EntityConveter extends Converter<VehicleDTO, VehicleEntity> {
}
