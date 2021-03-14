package com.teenthofabud.codingchallenge.sharenow.vehicle.filter;

import com.teenthofabud.codingchallenge.sharenow.vehicle.model.error.VehicleServiceException;
import com.teenthofabud.codingchallenge.sharenow.vehicle.model.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(VehicleServiceException.class)
    public ResponseEntity<ErrorVO> handleVehicleServiceException(VehicleServiceException vsex) {
        LOGGER.error("Error encountered: {}", vsex);
        ErrorVO vo = new ErrorVO();
        String msg = messageSource.getMessage(vsex.getError().getErrorCode(), null, Locale.US);
        msg = String.format(msg, vsex.getParams());
        vo.setErrorCode(vsex.getError().getErrorCode());
        vo.setErrorMessage(msg);
        ResponseEntity<ErrorVO>  response = ResponseEntity.status(vsex.getError().getStatusCode()).body(vo);
        return response;
    }

}
