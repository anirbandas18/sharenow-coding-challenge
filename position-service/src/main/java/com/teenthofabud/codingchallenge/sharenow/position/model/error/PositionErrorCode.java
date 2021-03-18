package com.teenthofabud.codingchallenge.sharenow.position.model.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PositionErrorCode {

    NOT_FOUND("SNCC-POSS-001", 404),
    INVALID_PARAMETER("SNCC-POSS-002", 400),
    SYSTEM_ERROR("SNCC-POSS-003", 500),
    UNEXPECTED_PARAMETER("SNCC-POSS-004", 500);

    @ToString.Include
    private int statusCode;
    @ToString.Include
    private String errorCode;

    private PositionErrorCode(String errorCode, int statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}
