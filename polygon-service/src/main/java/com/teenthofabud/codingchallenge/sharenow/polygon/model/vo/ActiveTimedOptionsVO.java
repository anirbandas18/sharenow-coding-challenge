package com.teenthofabud.codingchallenge.sharenow.polygon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ActiveTimedOptionsVO implements Serializable {

    private int min;
    private int max;
    private int idleTime;
    private int revenue;
    private int walkingRange1;
    private int walkingRange2;

}
