package com.teenthofabud.codingchallenge.sharenow.polygon.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.GeoJsonObject;

@Getter
@Setter
@NoArgsConstructor
public class GeoFeatureEntity {

    private String name;
    private GeoJsonObject geometry;

}
