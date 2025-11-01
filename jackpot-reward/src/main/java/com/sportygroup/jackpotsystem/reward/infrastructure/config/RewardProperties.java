package com.sportygroup.jackpotsystem.reward.infrastructure.config;

import com.sportygroup.jackpotsystem.reward.domain.RewardType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "jackpot.reward")
public class RewardProperties {

    private RewardType type = RewardType.FIXED;
    private FixedConfig fixed = new FixedConfig();
    private VariableConfig variable = new VariableConfig();

    @Data
    public static class FixedConfig {
        private BigDecimal winChance = new BigDecimal("0.001");
    }

    @Data
    public static class VariableConfig {
        private BigDecimal initialChance = new BigDecimal("0.001");
        private BigDecimal maxChance = new BigDecimal("0.01");
        private BigDecimal threshold = new BigDecimal("50000");
    }
}

