package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StrategicPolygonVO implements Serializable {

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
    private int version;
    private List<GeoFeatureVO> geoFeatures;

}
