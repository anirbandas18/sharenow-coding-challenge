package com.teenthofabud.codingchallenge.sharenow.polygon.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.GeoJsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("strategicPolygon")
public class StrategicPolygonEntity {

    @Id
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
    private List<GeoFeatureEntity> geoFeatures;
    private OptionsEntity options;
    private GeoJsonObject geometry;
    private int version;
    @JsonProperty("$computed")
    private ComputedEntity computed;
    private List<TimedOptionsEntity> timedOptions;

}
