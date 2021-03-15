package com.teenthofabud.codingchallenge.sharenow.car.model.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CarErrorCode {

    NOT_FOUND("SNCC-CS-001", 404),
    INVALID_PARAMETER("SNCC-CS-002", 400);
    @ToString.Include
    private int statusCode;
    @ToString.Include
    private String errorCode;

    private CarErrorCode(String errorCode, int statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


}
