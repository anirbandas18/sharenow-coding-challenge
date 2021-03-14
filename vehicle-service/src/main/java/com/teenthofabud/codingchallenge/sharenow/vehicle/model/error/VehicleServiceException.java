package com.teenthofabud.codingchallenge.sharenow.vehicle.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleServiceException extends Exception {

    private VehicleErrorCode error;
    private String message;
    private Object[] params;

    public VehicleServiceException(String message) {
        super(message);
        this.message = message;
    }

    public VehicleServiceException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public VehicleServiceException(String message, VehicleErrorCode error, Object[] params) {
        super(message);
        this.message = message;
        this.error = error;
        this.params = params;
    }


}
