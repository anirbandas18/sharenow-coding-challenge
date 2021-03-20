package com.teenthofabud.codingchallenge.sharenow.position.model.dto.car;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsDTO implements Comparable<CarDetailsDTO> {

    private int id;
    private int locationId;
    @EqualsAndHashCode.Include
    private String vin;
    private String numberPlate;
    private PositionDTO position;
    private float fuel;
    private String model;

    @Override
    public int compareTo(CarDetailsDTO o) {
        return this.vin.compareTo(o.getVin());
    }
}
