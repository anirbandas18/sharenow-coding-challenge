package com.teenthofabud.codingchallenge.sharenow.polygon.filter;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestServiceErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceErrorHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(PolygonServiceException.class)
    public ResponseEntity<ErrorVO> handleVehicleServiceException(PolygonServiceException psex) {
        LOGGER.error("Error encountered: {}", psex);
        ErrorVO vo = new ErrorVO();
        String msg = messageSource.getMessage(psex.getError().getErrorCode(), null, Locale.US);
        msg = psex.getParams() != null ? String.format(msg, psex.getParams()) : msg;
        vo.setErrorCode(psex.getError().getErrorCode());
        vo.setErrorMessage(msg);
        ResponseEntity<ErrorVO>  response = ResponseEntity.status(psex.getError().getStatusCode()).body(vo);
        return response;
    }

}
