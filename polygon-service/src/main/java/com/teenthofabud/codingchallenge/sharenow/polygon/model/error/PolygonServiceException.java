package com.teenthofabud.codingchallenge.sharenow.polygon.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolygonServiceException extends Exception {

    private PolygonErrorCode error;
    private String message;
    private Object[] params;

    public PolygonServiceException(String message, PolygonErrorCode error) {
        super(message);
        this.message = message;
        this.error = error;
    }

    public PolygonServiceException(String message, PolygonErrorCode error, Object[] params) {
        super(message);
        this.message = message;
        this.error = error;
        this.params = params;
    }


}
