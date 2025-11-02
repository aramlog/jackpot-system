package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.contribution;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.JackpotStore;
import com.sportygroup.jackpotsystem.contribution.infrastructure.ContributionTestConfiguration;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TestApplication;
import com.sportygroup.jackpotsystem.core.BaseIntegrationTest;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.BetFeignClient;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TestApplication.class, ContributionTestConfiguration.class})
class ContributionControllerIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private ContributionFeignClient contributionFeignClient;

    @MockitoBean
    private BetFeignClient betFeignClient;

    @Autowired
    private ContributionStore contributionStore;

    @Autowired
    private JackpotStore jackpotStore;

    private UUID jackpotId;
    private UUID betId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        betId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    private Jackpot createTestJackpot() {
        var jackpot = new Jackpot(
                null,
                "Test Jackpot",
                ContributionType.FIXED,
                new BigDecimal("1000.00"),
                new BigDecimal("0.05"),
                null,
                null,
                null
        );
        var saved = jackpotStore.save(jackpot);
        jackpotId = saved.id();
        return saved;
    }

    @Test
    void shouldGetContributionsByJackpotId() throws Exception {
        // Given
        createTestJackpot();
        var stakeAmount = new BigDecimal("100.00");
        var contributionAmount = new BigDecimal("5.00");
        var currentJackpotAmount = new BigDecimal("1000.00");

        var contribution = new JackpotContribution(
                null,
                betId,
                userId,
                jackpotId,
                stakeAmount,
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );
        contributionStore.save(contribution);

        // When & Then
        mockMvc.perform(get("/internal/v1/contributions/jackpot/{jackpotId}", jackpotId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.contributions").isArray())
                .andExpect(jsonPath("$.contributions[0].betId").value(betId.toString()))
                .andExpect(jsonPath("$.contributions[0].jackpotId").value(jackpotId.toString()))
                .andExpect(jsonPath("$.contributions[0].stakeAmount").value(100.0))
                .andExpect(jsonPath("$.contributions[0].contributionAmount").value(5.0));
    }

    @Test
    void shouldGetContributionByBetId() throws Exception {
        // Given
        createTestJackpot();
        var stakeAmount = new BigDecimal("100.00");
        var contributionAmount = new BigDecimal("5.00");
        var currentJackpotAmount = new BigDecimal("1000.00");

        var contribution = new JackpotContribution(
                null,
                betId,
                userId,
                jackpotId,
                stakeAmount,
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );
        contributionStore.save(contribution);

        // When & Then
        mockMvc.perform(get("/internal/v1/contributions/bet/{betId}", betId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.betId").value(betId.toString()))
                .andExpect(jsonPath("$.jackpotId").value(jackpotId.toString()))
                .andExpect(jsonPath("$.stakeAmount").value(100.0))
                .andExpect(jsonPath("$.contributionAmount").value(5.0));
    }

    @Test
    void shouldReturnNotFoundWhenContributionDoesNotExist() throws Exception {
        // Given
        var nonExistentBetId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(get("/internal/v1/contributions/bet/{betId}", nonExistentBetId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteContributionsByJackpotId() throws Exception {
        // Given
        createTestJackpot();
        var stakeAmount = new BigDecimal("100.00");
        var contributionAmount = new BigDecimal("5.00");
        var currentJackpotAmount = new BigDecimal("1000.00");

        var contribution = new JackpotContribution(
                null,
                betId,
                userId,
                jackpotId,
                stakeAmount,
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );
        contributionStore.save(contribution);

        // When & Then
        mockMvc.perform(delete("/internal/v1/contributions/jackpot/{jackpotId}", jackpotId))
                .andExpect(status().isNoContent());

        // Verify contributions are deleted
        mockMvc.perform(get("/internal/v1/contributions/jackpot/{jackpotId}", jackpotId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contributions").isArray())
                .andExpect(jsonPath("$.contributions").isEmpty());
    }
}

