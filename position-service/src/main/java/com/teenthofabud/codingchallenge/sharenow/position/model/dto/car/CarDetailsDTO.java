package com.teenthofabud.codingchallenge.sharenow.position.model.dto.car;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarDetailsDTO {

    private int id;
    private int locationId;
    @EqualsAndHashCode.Include
    private String vin;
    private String numberPlate;
    private PositionDTO position;
    private float fuel;
    private String model;

}
