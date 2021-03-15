package com.teenthofabud.codingchallenge.sharenow.polling.cleanup.service;

import org.springframework.stereotype.Service;

@Service
public interface CarCleanupService {

    public void clearStaleCarsForConfiguredCity();

}
