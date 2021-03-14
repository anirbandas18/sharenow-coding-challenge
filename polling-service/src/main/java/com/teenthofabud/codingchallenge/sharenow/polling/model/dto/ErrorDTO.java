package com.teenthofabud.codingchallenge.sharenow.polling.refresh.model.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorDTO {

    private int statusCode;
    private String error;
    private String message;


}
