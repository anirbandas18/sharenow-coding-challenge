package com.teenthofabud.codingchallenge.sharenow.polygon.filter;

import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.error.PolygonServiceException;
import com.teenthofabud.codingchallenge.sharenow.polygon.repository.PolygonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DataSeedingHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSeedingHandler.class);

    private RestTemplate restClient;

    @Value("${polygon.dump.stuttgart.url}")
    private String polygonDataDumpURLForStuttgart;

    @Autowired
    private RestClientErrorHandler errorHandler;

    @Autowired
    private PolygonRepository repository;


    @PostConstruct
    private void init() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restClient = new RestTemplate();
        this.restClient.setErrorHandler(errorHandler);
        this.restClient.setMessageConverters(messageConverters);
    }

    /**
     * Capture the context refreshed event which denotes that all beans have been initialized and the application context is ready
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        try {
            List<StrategicPolygonEntity> collection = input();
            LOGGER.info("Received input size: ", collection.size());
            int count = output(collection);
            LOGGER.info("Seeding successful: {}", count == collection.size());
        } catch (PolygonServiceException e) {
            e.printStackTrace();
        }
    }

    private List<StrategicPolygonEntity> input() throws PolygonServiceException {
        List<StrategicPolygonEntity> collection = new ArrayList<>();
        LOGGER.info("Requesting : {}", polygonDataDumpURLForStuttgart);
        ResponseEntity<List<StrategicPolygonEntity>> responseEntity =
                restClient.exchange(polygonDataDumpURLForStuttgart, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StrategicPolygonEntity>>() {});
        collection = responseEntity.getBody();
        LOGGER.info("Received response: {}", collection != null);
        return collection;
    }

    private int output(List<StrategicPolygonEntity> collection) {
        int count = 0;
        for(StrategicPolygonEntity entity : collection) {
            StrategicPolygonEntity saved = this.repository.save(entity);
            if(saved != null) {
                count++;
                LOGGER.debug("Saved strategic polygon with id: {}", saved.getId());
            }
        }
        LOGGER.info("Saved {} strategic polygons", count);
        return count;
    }
}
