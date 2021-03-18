package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TimedOptionsDTO implements Serializable {

    private String key;
    private List<List<Double>> changesOverTime;

}
