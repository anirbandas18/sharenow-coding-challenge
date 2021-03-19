package com.teenthofabud.codingchallenge.sharenow.car.model.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity  implements Serializable {

    @ToString.Include
    private double latitude;
    @ToString.Include
    private double longitude;

}
