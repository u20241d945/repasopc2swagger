package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MiningEquipmentUnitsControllerIntegrationTests {

    private static final String ENDPOINT = "/api/v1/equipment-units";
    private static final String SERIAL_NUMBER = "CAT-793F-INTEGRATION-001";

    private final MockMvc mockMvc;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    MiningEquipmentUnitsControllerIntegrationTests(MockMvc mockMvc, JdbcTemplate jdbcTemplate) {
        this.mockMvc = mockMvc;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    void openApiAndSwaggerUiExposeTheRegistrationEndpoint() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/api/v1/equipment-units")));

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    void registrationFlowEncryptsSerialAndMapsExpectedErrors() throws Exception {
        String validRequest = """
                {
                  "model": "CAT 793F",
                  "serialNumber": "%s",
                  "operationStatus": "ACTIVE",
                  "assignedMineSite": "Antamina",
                  "gpsLatitude": -9.531,
                  "gpsLongitude": -77.059,
                  "hoursOfOperation": 1250
                }
                """.formatted(SERIAL_NUMBER);

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(validRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.equipmentUnitId").isNotEmpty())
                .andExpect(jsonPath("$.serialNumber").value(SERIAL_NUMBER))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());

        String storedSerial = jdbcTemplate.queryForObject(
                "select serial_number from mining_equipment_units where assigned_mine_site = ?",
                String.class,
                "Antamina");
        assertThat(storedSerial).isNotEqualTo(SERIAL_NUMBER);

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(validRequest))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("MINING_EQUIPMENT_UNIT_CONFLICT"));

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(validRequest.replace("-9.531", "91.0")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(validRequest
                                .replace(SERIAL_NUMBER, SERIAL_NUMBER + "-INVALID-STATUS")
                                .replace("ACTIVE", "BROKEN")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
