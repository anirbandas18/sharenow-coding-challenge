package com.teenthofabud.codingchallenge.sharenow.polygon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class StrategicPolygonDetailedVO implements Serializable {

    private String id;
    private Date updatedAt;
    private Date createdAt;
    private int v;
    private String name;
    private String cityId;
    private String legacyId;
    private String type;
    private List<GeoFeatureVO> geoFeatures;
    private OptionsVO options;
    private Polygon geometry;
    private int version;
    private ComputedVO computed;
    private List<TimedOptionsVO> timedOptions;

}
