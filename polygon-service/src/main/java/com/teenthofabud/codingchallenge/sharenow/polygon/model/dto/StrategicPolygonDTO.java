package com.teenthofabud.codingchallenge.sharenow.polygon.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.GeoJsonObject;
import org.geojson.Polygon;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StrategicPolygonDTO implements Serializable {

    @JsonProperty("_id")
    private String id;
    private Date updatedAt;
    private Date createdAt;
    @JsonProperty("__v")
    private int v;
    private String name;
    private String cityId;
    private String legacyId;
    private String type;
    private List<GeoFeatureDTO> geoFeatures;
    private OptionsDTO options;
    private Polygon geometry;
    private int version;
    @JsonProperty("$computed")
    private ComputedDTO computed;
    private List<TimedOptionsDTO> timedOptions;

}
