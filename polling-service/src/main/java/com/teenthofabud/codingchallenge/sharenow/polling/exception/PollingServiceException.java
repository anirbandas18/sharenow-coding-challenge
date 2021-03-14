package com.teenthofabud.codingchallenge.sharenow.polling.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollingServiceException extends RuntimeException {

    private PollingErrorCode errorCode;
    private String message;

    public PollingServiceException(String message) {
        super(message);
        this.message = message;
    }

    public PollingServiceException(String message, PollingErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

}
