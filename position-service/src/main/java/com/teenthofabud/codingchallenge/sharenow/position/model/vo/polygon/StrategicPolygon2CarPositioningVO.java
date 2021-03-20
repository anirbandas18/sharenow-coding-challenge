package com.teenthofabud.codingchallenge.sharenow.position.model.vo.polygon;

import com.teenthofabud.codingchallenge.sharenow.position.model.vo.car.CarMappedVO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class StrategicPolygon2CarPositioningVO implements Comparable<StrategicPolygon2CarPositioningVO>{

    private Set<CarMappedVO> cars;
    private StrategicPolygonMappedVO polygon;

    public StrategicPolygon2CarPositioningVO() {
        this.cars = new TreeSet<>();
        this.polygon = new StrategicPolygonMappedComplexVO();
    }

    public StrategicPolygon2CarPositioningVO(Set<CarMappedVO> cars, StrategicPolygonMappedVO polygon) {
        this.cars = cars;
        this.polygon = polygon;
    }

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

    public List<CarMappedVO> getCars() {
        List<CarMappedVO> carMappedVOList = new ArrayList<>();
        if(this.cars != null) {
            carMappedVOList = new ArrayList<>(this.cars);
        }
        return carMappedVOList;
    }

    @Override
    public int compareTo(StrategicPolygon2CarPositioningVO o) {
        return this.polygon.getId().compareTo(o.getPolygon().getId());
    }
}
