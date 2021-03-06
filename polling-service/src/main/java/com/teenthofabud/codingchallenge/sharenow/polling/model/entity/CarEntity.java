package com.teenthofabud.codingchallenge.sharenow.polling.model.entity;

import lombok.Getter;
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
@RedisHash("Car")
@TypeAlias("Car")
public class CarEntity implements Serializable {

    @Indexed
    private int id;
    private int locationId;
    @Id
    @Indexed
    private String vin;
    private String numberPlate;
    private PositionEntity position;
    private float fuel;
    private String model;
    private Date updatedAt;

    public String getCacheKey() {
        return "Car:" + vin;
    }

}
