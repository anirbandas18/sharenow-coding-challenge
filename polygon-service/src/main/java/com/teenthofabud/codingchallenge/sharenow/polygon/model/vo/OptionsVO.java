package com.teenthofabud.codingchallenge.sharenow.polygon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OptionsVO implements Serializable {

    private boolean active;
    private boolean isExcluded;
    private double area;

}
