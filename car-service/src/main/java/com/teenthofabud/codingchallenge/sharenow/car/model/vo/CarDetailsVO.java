package com.teenthofabud.codingchallenge.sharenow.car.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDetailsVO implements Comparable<CarDetailsVO> {

    private int id;
    private int locationId;
    private String vin;
    private String numberPlate;
    private PositionVO position;
    private float fuel;
    private String model;

    @Override
    public int compareTo(CarDetailsVO o) {
        return this.vin.compareTo(o.getVin());
    }
}
