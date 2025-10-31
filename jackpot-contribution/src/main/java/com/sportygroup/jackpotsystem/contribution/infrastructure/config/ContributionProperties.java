package com.sportygroup.jackpotsystem.contribution.infrastructure.config;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "jackpot.contribution")
public class ContributionProperties {

    private ContributionType type = ContributionType.FIXED;
    private FixedConfig fixed = new FixedConfig();
    private VariableConfig variable = new VariableConfig();

    @Data
    public static class FixedConfig {
        private BigDecimal percentage;
    }

    @Data
    public static class VariableConfig {
        private BigDecimal initialPercentage;
        private BigDecimal minPercentage;
        private BigDecimal threshold;
    }
}

