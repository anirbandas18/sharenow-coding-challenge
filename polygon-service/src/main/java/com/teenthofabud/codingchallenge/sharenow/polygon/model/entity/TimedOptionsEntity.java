package com.teenthofabud.codingchallenge.sharenow.polygon.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TimedOptionsEntity {

    private String key;
    private List<List<Double>> changesOverTime;

}
