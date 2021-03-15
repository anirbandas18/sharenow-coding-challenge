package com.teenthofabud.codingchallenge.sharenow.polygon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.sharenow.polygon.model.entity.StrategicPolygonEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;

public class TestDriver {

    public static void main(String[] args) throws IOException {
        Logger log = Logger.getLogger(TestDriver.class.getCanonicalName());
        ObjectMapper om = new ObjectMapper();
        //om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url = new URL("https://gist.githubusercontent.com/codeofsumit/6540cdb245bd14c33b486b7981981b7b/raw/73ebda86c32923e203b2a8e61615da3e5f1695a2/polygons.json");
        URLConnection connection = url.openConnection();
        log.info("Opened connection");
        InputStream is = connection.getInputStream();
        log.info("Received response");
        List<StrategicPolygonEntity> collection = om.readValue(is, new TypeReference<List<StrategicPolygonEntity>>() {});
        log.info("Parsed response");
        log.info("Received features collection count: " + collection.size());
        is.close();
        log.info("Closed connection");
    }

}
