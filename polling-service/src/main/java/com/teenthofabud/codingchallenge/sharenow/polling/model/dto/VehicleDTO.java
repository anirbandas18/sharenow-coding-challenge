package com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {

    private int id;
    private int locationId;
    private String vin;
    private String numberPlate;
    private PositionDTO position;
    private float fuel;
    private String model;

}
