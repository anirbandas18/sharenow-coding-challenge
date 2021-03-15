package com.teenthofabud.codingchallenge.sharenow.car.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarServiceException extends Exception {

    private CarErrorCode error;
    private String message;
    private Object[] params;

    public CarServiceException(String message) {
        super(message);
        this.message = message;
    }

    public CarServiceException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public CarServiceException(String message, CarErrorCode error, Object[] params) {
        super(message);
        this.message = message;
        this.error = error;
        this.params = params;
    }


}
