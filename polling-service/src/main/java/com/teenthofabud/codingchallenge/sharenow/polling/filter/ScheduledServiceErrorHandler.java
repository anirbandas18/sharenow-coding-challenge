package com.teenthofabud.codingchallenge.sharenow.polling.filter;

import com.teenthofabud.codingchallenge.sharenow.polling.exception.PollingErrorCode;
import com.teenthofabud.codingchallenge.sharenow.polling.exception.PollingServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ScheduledServiceErrorHandler implements TaskSchedulerCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledServiceErrorHandler.class);

    @Override
    public void customize(ThreadPoolTaskScheduler taskScheduler) {
        taskScheduler.setErrorHandler(t -> {
            LOGGER.error("Error executing scheduled task: {}", t.getMessage());
            t.printStackTrace();
        });
    }
}
