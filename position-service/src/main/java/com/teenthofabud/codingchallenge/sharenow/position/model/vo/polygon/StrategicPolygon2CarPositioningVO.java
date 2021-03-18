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
public class StrategicPolygon2CarPositioningVO {

    private List<CarMappedVO> cars;
    private StrategicPolygonMappedVO polygon;

    public void addCar(CarMappedVO carVO) {
        if(this.cars != null) {
            this.cars.add(carVO);
        }
    }

    public boolean hasCars() {
        if(this.cars != null) {
            return !this.cars.isEmpty();
        } else {
            return false;
        }
    }

}
