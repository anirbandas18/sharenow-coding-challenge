package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.GeoJsonObject;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GeoFeatureVO implements Serializable {

    private String name;
    private GeoJsonObject geometry;

}