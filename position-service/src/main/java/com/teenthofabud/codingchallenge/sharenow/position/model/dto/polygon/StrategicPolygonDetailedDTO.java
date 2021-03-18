package com.teenthofabud.codingchallenge.sharenow.position.model.dto.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geojson.Polygon;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StrategicPolygonDetailedDTO extends StrategicPolygonDTO {

    private OptionsDTO options;
    private Polygon geometry;
    @JsonProperty("$computed")
    private ComputedDTO computed;
    private List<TimedOptionsDTO> timedOptions;

}
