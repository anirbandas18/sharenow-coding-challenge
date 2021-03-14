package com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@RedisHash("Vehicle")
public class VehicleEntity implements Serializable {

    @Id
    private int id;
    private int locationId;
    private String vin;
    private String numberPlate;
    private PositionEntity position;
    private float fuel;
    private String model;
    private Date updatedAt;

    public String getCacheKey() {
        return "Vehicle:" + id;
    }

}
