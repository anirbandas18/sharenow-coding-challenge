package com.teenthofabud.codingchallenge.sharenow.polling.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class RestClientErrorHandler implements ResponseErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientErrorHandler.class);

    private ObjectMapper om;

    @PostConstruct
    private void init() {
        this.om = new ObjectMapper();
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            ErrorDTO err = om.readValue(clientHttpResponse.getBody(), ErrorDTO.class);
            LOGGER.error("Unexpected server error: [{}={}]", clientHttpResponse.getRawStatusCode(), err.toString());
        } else if (clientHttpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            ErrorDTO err = om.readValue(clientHttpResponse.getBody(), ErrorDTO.class);
            if (clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.error("Invalid client input: [locationName {}]", err.getMessage());
            } else {
                LOGGER.error("Unexpected client error: [{}={}]", clientHttpResponse.getRawStatusCode(), err.toString());
            }
        }
    }
}
