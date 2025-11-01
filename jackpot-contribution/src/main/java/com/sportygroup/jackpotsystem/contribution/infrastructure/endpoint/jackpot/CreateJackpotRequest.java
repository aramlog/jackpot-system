package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.CreateJackpotCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateJackpotRequest {

    @NotBlank
    @Schema(
            description = "Jackpot name",
            example = "Fixed Percentage Jackpot")
    String name;

    @NotNull
    @Schema(
            description = "Contribution type",
            example = "FIXED")
    ContributionType contributionType;

    @NotNull
    @DecimalMin("0.0")
    @Schema(
            description = "Initial pool amount",
            example = "1000.00")
    BigDecimal initialPool;

    @Schema(
            description = "Fixed percentage (required for FIXED type)",
            example = "0.05")
    BigDecimal fixedPercentage;

    @Schema(
            description = "Variable initial percentage (required for VARIABLE type)",
            example = "0.05")
    BigDecimal variableInitialPercentage;

    @Schema(
            description = "Variable minimum percentage (required for VARIABLE type)",
            example = "0.02")
    BigDecimal variableMinPercentage;

    @Schema(
            description = "Variable threshold (required for VARIABLE type)",
            example = "10000.00")
    BigDecimal variableThreshold;

    public CreateJackpotCommand.Input toInput() {
        return new CreateJackpotCommand.Input(
                getName(),
                getContributionType(),
                getInitialPool(),
                getFixedPercentage(),
                getVariableInitialPercentage(),
                getVariableMinPercentage(),
                getVariableThreshold()
        );
    }
}

