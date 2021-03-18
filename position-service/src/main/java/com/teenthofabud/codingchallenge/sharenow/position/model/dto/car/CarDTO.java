package com.teenthofabud.codingchallenge.sharenow.position.model.dto.car;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarDTO {

    private int id;
    @EqualsAndHashCode.Include
    private String vin;
    private String numberPlate;
    private int locationId;

}
