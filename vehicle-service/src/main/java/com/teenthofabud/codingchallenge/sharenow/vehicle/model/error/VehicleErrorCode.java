package com.teenthofabud.codingchallenge.sharenow.vehicle.model.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum VehicleErrorCode {

    NOT_FOUND("SNCC-VS-001", 404),
    INVALID_PARAMETER("SNCC-VS-002", 400);
    @ToString.Include
    private int statusCode;
    @ToString.Include
    private String errorCode;

    private VehicleErrorCode(String errorCode, int statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


}
