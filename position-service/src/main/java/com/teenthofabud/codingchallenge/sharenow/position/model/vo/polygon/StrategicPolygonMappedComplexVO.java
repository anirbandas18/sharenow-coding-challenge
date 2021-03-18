package com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon;

import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.CarMappedVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StrategicPolygonMappedComplexVO extends StrategicPolygonMappedVO {

    private List<CarMappedVO> cars;

    public void addCar(CarMappedVO carVO) {
        if(this.cars != null ) {
            this.cars.add(carVO);
        }
    }

}
