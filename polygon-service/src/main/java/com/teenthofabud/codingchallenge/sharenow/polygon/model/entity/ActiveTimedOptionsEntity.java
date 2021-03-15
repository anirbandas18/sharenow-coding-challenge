package com.teenthofabud.codingchallenge.sharenow.polygon.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActiveTimedOptionsEntity {

    private int min;
    private int max;
    @JsonProperty("idle_time")
    private int idleTime;
    private int revenue;
    @JsonProperty("walking_range1")
    private int walkingRange1;
    @JsonProperty("walking_range2")
    private int walkingRange2;

}
