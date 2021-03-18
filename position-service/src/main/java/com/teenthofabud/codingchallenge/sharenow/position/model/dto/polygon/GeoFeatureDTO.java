package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.Point;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GeoFeatureDTO implements Serializable {

    private String name;
    private Point geometry;

}
