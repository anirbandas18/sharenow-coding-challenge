package com.teenthofabud.codingchallenge.sharenow.polling.refresh;

import com.teenthofabud.codingchallenge.sharenow.polling.model.dto.PositionDTO;
import com.teenthofabud.codingchallenge.sharenow.polling.model.entity.PositionEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionDTO2EntityConversionTests {

    private static PositionDTO dto;

    private static PositionEntity entity;

    @BeforeAll
    private static void init() {
        dto = new PositionDTO();
        dto.setLatitude(1.1d);
        dto.setLongitude(9.9d);

        entity = new PositionEntity();
        entity.setLatitude(1.1d);
        entity.setLongitude(9.9d);
    }

    @Test
    @DisplayName("Test PositionDTO to PositionEntity Conversion")
    public void testConversion_OfPositionDTO_ToPositionEntity() {

    }

}
