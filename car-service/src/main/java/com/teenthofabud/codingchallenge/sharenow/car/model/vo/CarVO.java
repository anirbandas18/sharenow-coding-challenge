package com.teenthofabud.codingchallenge.sharenow.car.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {

    private int id;
    private String vin;
    private String numberPlate;
    private int locationId;

}
