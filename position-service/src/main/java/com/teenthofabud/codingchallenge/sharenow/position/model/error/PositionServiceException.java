package com.teenthofabud.codingchallenge.sharenow.position.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionServiceException extends Exception {

    private PositionErrorCode error;
    private String message;
    private Object[] params;

    public PositionServiceException(String message, PositionErrorCode error) {
        super(message);
        this.message = message;
        this.error = error;
    }

    public PositionServiceException(String message, PositionErrorCode error, Object[] params) {
        super(message);
        this.message = message;
        this.error = error;
        this.params = params;
    }


}
