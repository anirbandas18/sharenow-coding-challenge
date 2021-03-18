package com.teenthofabud.codingchallenge.sharenow.position.model.vo.car;

import com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon.StrategicPolygonMappedVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car2StrategicPolygonPositioningVO {

    private CarMappedVO car;
    private StrategicPolygonMappedVO polygon;

}
