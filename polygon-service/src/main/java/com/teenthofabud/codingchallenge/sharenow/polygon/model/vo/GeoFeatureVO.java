package com.teenthofabud.codingchallenge.sharenow.polygon.model.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.Point;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GeoFeatureVO implements Serializable {

    private String name;
    private Point geometry;

}
