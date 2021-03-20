package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OptionsDTO implements Serializable {

    private boolean active;
    private boolean isExcluded;
    private double area;

    public void setIsExcluded(boolean isExcluded) {
        this.isExcluded = isExcluded;
    }

    public boolean getIsExcluded() {
        return this.isExcluded;
    }

}
