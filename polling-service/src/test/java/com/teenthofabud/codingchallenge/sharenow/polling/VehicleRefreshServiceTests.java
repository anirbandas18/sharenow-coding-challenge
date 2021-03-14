package com.teenthofabud.codingchallenge.sharenow.polling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class VehicleRefreshServiceTests {

    @MockBean
    private RestTemplate restClient;

    @BeforeEach
    public void init() {
        this.restClient = new RestTemplate();
    }

    @Test
    @DisplayName("Test vehicle search for correct location should respond a vehicle list")
    public void testQueryingVehiclesAPI_ForCorrectLocation_ShouldReturnList_WithHttpStatus200() {

    }

    @Test
    @DisplayName("Test vehicle search for incorrect location should respond with an error")
    public void testQueryingVehiclesAPI_ForIncorrectLocation_ShouldReturnError_WithHttpStatus404() {

    }

}
