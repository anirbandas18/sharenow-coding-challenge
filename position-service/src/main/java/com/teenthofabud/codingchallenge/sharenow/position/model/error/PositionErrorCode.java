package com.teenthofabud.codingchallenge.sharenow.position.model.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PositionErrorCode {

    NOT_FOUND("SNCC-PLYS-001", 404),
    INVALID_PARAMETER("SNCC-PLYS-002", 500),
    SYSTEM_ERROR("SNCC-PLYS-003", 400);

    @ToString.Include
    private int statusCode;
    @ToString.Include
    private String errorCode;

    private PositionErrorCode(String errorCode, int statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


}
