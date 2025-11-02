package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.infrastructure.BetTestConfiguration;
import com.sportygroup.jackpotsystem.bet.infrastructure.TestApplication;
import com.sportygroup.jackpotsystem.bet.infrastructure.messaging.BetEventPublisher;
import com.sportygroup.jackpotsystem.core.BaseIntegrationTest;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.BetFeignClient;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TestApplication.class, BetTestConfiguration.class})
class BetControllerIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private BetEventPublisher betEventPublisher;

    @MockitoBean
    private ContributionFeignClient contributionFeignClient;

    @MockitoBean
    private BetFeignClient betFeignClient;

    @Test
    void shouldPlaceBet() throws Exception {
        // Given
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");
        var request = new PlaceBetRequest(userId, jackpotId, betAmount);

        doNothing().when(betEventPublisher).publish(Mockito.any(Bet.class));
        when(betEventPublisher.getTopic()).thenReturn("jackpot-bets");

        // When & Then
        mockMvc.perform(post("/public/v1/bets")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.betId").exists())
                .andExpect(jsonPath("$.topic").value("jackpot-bets"));
    }

    @Test
    void shouldReturnBadRequestWhenPlacingBetWithInvalidData() throws Exception {
        // Given
        var request = new PlaceBetRequest(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("-10.00"));

        // When & Then
        mockMvc.perform(post("/public/v1/bets")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBetById() throws Exception {
        // Given
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");
        var placeRequest = new PlaceBetRequest(userId, jackpotId, betAmount);

        doNothing().when(betEventPublisher).publish(Mockito.any(Bet.class));
        when(betEventPublisher.getTopic()).thenReturn("jackpot-bets");

        var placeResult = mockMvc.perform(post("/public/v1/bets")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(placeRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseJson = placeResult.getResponse().getContentAsString();
        var betId = objectMapper.readTree(responseJson).get("betId").asText();

        // When & Then
        mockMvc.perform(get("/internal/v1/bets/{betId}", betId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.betId").value(betId))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.jackpotId").value(jackpotId.toString()))
                .andExpect(jsonPath("$.betAmount").value(100.0));
    }

    @Test
    void shouldReturnNotFoundWhenBetDoesNotExist() throws Exception {
        // Given
        var nonExistentBetId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(get("/internal/v1/bets/{betId}", nonExistentBetId))
                .andExpect(status().isNotFound());
    }
}

