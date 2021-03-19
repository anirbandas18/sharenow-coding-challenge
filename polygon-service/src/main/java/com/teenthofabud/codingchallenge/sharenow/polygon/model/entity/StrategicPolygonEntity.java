package com.teenthofabud.codingchallenge.sharenow.polygon.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.Polygon;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("strategicPolygon")
public class StrategicPolygonEntity {

    @Id
    @Indexed
    @JsonProperty("_id")
    private String id;
    private Date updatedAt;
    private Date createdAt;
    @JsonProperty("__v")
    private int v;
    @Indexed
    private String name;
    @Indexed
    private String cityId;
    private String legacyId;
    @Indexed
    private String type;
    private List<GeoFeatureEntity> geoFeatures;
    private OptionsEntity options;
    private Polygon geometry;
    private int version;
    @JsonProperty("$computed")
    private ComputedEntity computed;
    private List<TimedOptionsEntity> timedOptions;

}
