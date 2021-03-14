package com.teenthofabud.codingchallenge.sharenow.vehicle.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@RedisHash("Vehicle")
@TypeAlias("Vehicle")
@NoArgsConstructor
public class VehicleEntity implements Serializable {

    @Id
    private int id;
    private int locationId;
    @Indexed
    private String vin;
    private String numberPlate;
    private PositionEntity position;
    private float fuel;
    private String model;
    private Date updatedAt;

    public String getCacheKey() {
        return "Vehicle:" + id;
    }

    public VehicleEntity(String vin) {
        this.vin = vin;
    }

}
