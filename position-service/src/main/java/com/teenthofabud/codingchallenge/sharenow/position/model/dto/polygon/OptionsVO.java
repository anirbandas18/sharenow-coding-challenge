package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

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
    @JsonProperty("is_excluded")
    private boolean isExcluded;
    private double area;

}
