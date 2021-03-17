package com.teenthofabud.codingchallenge.sharenow.position.service.impl;

import com.teenthofabud.codingchallenge.sharenow.position.repository.CarServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.repository.PolygonServiceClient;
import com.teenthofabud.codingchallenge.sharenow.position.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionServiceImpl implements PositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Autowired
    private CarServiceClient carClient;

    @Autowired
    private PolygonServiceClient paolygonClient;



}
