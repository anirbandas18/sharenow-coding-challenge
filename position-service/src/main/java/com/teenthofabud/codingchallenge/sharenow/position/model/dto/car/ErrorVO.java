package com.teenthofabud.codingchallenge.sharenow.position.model.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorVO {

    private String errorCode;
    private String errorMessage;

}
