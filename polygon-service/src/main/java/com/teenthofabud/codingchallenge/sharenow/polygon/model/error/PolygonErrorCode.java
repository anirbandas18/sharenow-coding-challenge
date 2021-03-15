package com.teenthofabud.codingchallenge.sharenow.polygon.model.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PolygonErrorCode {

    NOT_FOUND("SNCC-PS-001", 404),
    INVALID_PARAMETER("SNCC-PS-002", 500),
    SYSTEM_ERROR("SNCC-PS-003", 400);

    @ToString.Include
    private int statusCode;
    @ToString.Include
    private String errorCode;

    private PolygonErrorCode(String errorCode, int statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


}
