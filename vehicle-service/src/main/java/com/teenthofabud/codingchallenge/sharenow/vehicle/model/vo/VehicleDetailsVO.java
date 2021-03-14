package com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDetailsVO {

    private int id;
    private int locationId;
    private String vin;
    private String numberPlate;
    private PositionVO position;
    private float fuel;
    private String model;

}
