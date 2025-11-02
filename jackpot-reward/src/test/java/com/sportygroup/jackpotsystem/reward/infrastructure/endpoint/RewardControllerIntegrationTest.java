package com.sportygroup.jackpotsystem.reward.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.core.BaseIntegrationTest;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.BetFeignClient;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import com.sportygroup.jackpotsystem.reward.infrastructure.RewardTestConfiguration;
import com.sportygroup.jackpotsystem.reward.infrastructure.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TestApplication.class, RewardTestConfiguration.class})
class RewardControllerIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private ContributionFeignClient contributionFeignClient;

    @MockitoBean
    private BetFeignClient betFeignClient;

    @Test
    void shouldEvaluateRewardAndWin() throws Exception {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var stakeAmount = new BigDecimal("100.00");
        var contributionAmount = new BigDecimal("5.00");
        var currentJackpotAmount = new BigDecimal("5000.00");

        var contributionItem = new ContributionFeignClient.ContributionItem(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                stakeAmount,
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );

        when(contributionFeignClient.getContributionByBetId(betId))
                .thenReturn(ResponseEntity.ok(contributionItem));
        when(contributionFeignClient.deleteContributionsByJackpotId(any(UUID.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        var request = new EvaluateRewardRequest(betId);

        // When & Then
        mockMvc.perform(post("/public/v1/rewards/evaluate")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.win").exists())
                .andExpect(jsonPath("$.rewardAmount").exists());

        verify(contributionFeignClient, times(1)).getContributionByBetId(betId);
    }

    @Test
    void shouldEvaluateRewardAndLose() throws Exception {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var stakeAmount = new BigDecimal("100.00");
        var contributionAmount = new BigDecimal("5.00");
        var currentJackpotAmount = new BigDecimal("5000.00");

        var contributionItem = new ContributionFeignClient.ContributionItem(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                stakeAmount,
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );

        when(contributionFeignClient.getContributionByBetId(betId))
                .thenReturn(ResponseEntity.ok(contributionItem));

        var request = new EvaluateRewardRequest(betId);

        // When & Then
        mockMvc.perform(post("/public/v1/rewards/evaluate")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.win").exists())
                .andExpect(jsonPath("$.rewardAmount").exists());

        verify(contributionFeignClient, times(1)).getContributionByBetId(betId);
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        // Given
        var request = "{}";

        // When & Then
        mockMvc.perform(post("/public/v1/rewards/evaluate")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
}

