package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import com.sportygroup.jackpotsystem.contribution.infrastructure.ContributionTestConfiguration;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TestApplication;
import com.sportygroup.jackpotsystem.core.BaseIntegrationTest;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.BetFeignClient;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TestApplication.class, ContributionTestConfiguration.class})
class JackpotControllerIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private ContributionFeignClient contributionFeignClient;

    @MockitoBean
    private BetFeignClient betFeignClient;

    @Test
    void shouldCreateFixedJackpot() throws Exception {
        // Given
        var request = new CreateJackpotRequest(
                "Fixed Jackpot",
                ContributionType.FIXED,
                new BigDecimal("1000.00"),
                new BigDecimal("0.05"),
                null,
                null,
                null
        );

        // When & Then
        mockMvc.perform(post("/protected/v1/jackpots")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.jackpotId").exists())
                .andExpect(jsonPath("$.name").value("Fixed Jackpot"));
    }

    @Test
    void shouldCreateVariableJackpot() throws Exception {
        // Given
        var request = new CreateJackpotRequest(
                "Variable Jackpot",
                ContributionType.VARIABLE,
                new BigDecimal("5000.00"),
                null,
                new BigDecimal("0.05"),
                new BigDecimal("0.02"),
                new BigDecimal("10000.00")
        );

        // When & Then
        mockMvc.perform(post("/protected/v1/jackpots")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.jackpotId").exists())
                .andExpect(jsonPath("$.name").value("Variable Jackpot"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        // Given - invalid request (missing required fields)
        var request = "{}";

        // When & Then
        mockMvc.perform(post("/protected/v1/jackpots")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInitialPoolIsNegative() throws Exception {
        // Given
        var request = new CreateJackpotRequest(
                "Invalid Jackpot",
                ContributionType.FIXED,
                new BigDecimal("-10.00"),
                new BigDecimal("0.05"),
                null,
                null,
                null
        );

        // When & Then
        mockMvc.perform(post("/protected/v1/jackpots")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

