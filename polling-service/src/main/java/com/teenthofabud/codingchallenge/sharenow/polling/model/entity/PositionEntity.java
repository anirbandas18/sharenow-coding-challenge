package com.teenthofabud.codingchallenge.sharenow.polling.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PositionEntity  implements Serializable {

    @ToString.Include
    private double latitude;
    @ToString.Include
    private double longitude;

}
