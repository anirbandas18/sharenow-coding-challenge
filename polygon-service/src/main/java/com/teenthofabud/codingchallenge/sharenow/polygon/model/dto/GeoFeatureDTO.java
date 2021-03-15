package com.teenthofabud.codingchallenge.sharenow.polygon.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.GeoJsonObject;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GeoFeatureDTO implements Serializable {

    private String name;
    private GeoJsonObject geometry;

}
