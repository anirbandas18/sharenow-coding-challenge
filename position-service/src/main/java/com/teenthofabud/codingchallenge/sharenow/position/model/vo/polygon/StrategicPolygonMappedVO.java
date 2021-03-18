package com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StrategicPolygonMappedVO {

    private String id;
    private String cityId;
    private String name;
    private String type;
    private List<GeoFeatureVO> geoFeatures;


}
