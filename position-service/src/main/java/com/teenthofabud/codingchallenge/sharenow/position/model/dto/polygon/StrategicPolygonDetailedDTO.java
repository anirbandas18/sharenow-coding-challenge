package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.Polygon;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StrategicPolygonDetailedDTO {


    private String id;
    private Date updatedAt;
    private Date createdAt;
    private int v;
    private String name;
    private String cityId;
    private String legacyId;
    private String type;
    private int version;
    private List<GeoFeatureDTO> geoFeatures;
    private OptionsDTO options;
    private Polygon geometry;
    private ComputedDTO computed;
    private List<TimedOptionsDTO> timedOptions;

}
