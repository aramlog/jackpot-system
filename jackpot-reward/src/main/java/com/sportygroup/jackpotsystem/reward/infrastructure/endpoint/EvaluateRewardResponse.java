package com.sportygroup.jackpotsystem.reward.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.reward.domain.EvaluateRewardCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class EvaluateRewardResponse {

    @Schema(
            description = "Whether the bet won",
            example = "true")
    boolean isWin;

    @Schema(
            description = "Reward amount (0 if no win)",
            example = "5000.00")
    BigDecimal rewardAmount;

    public static EvaluateRewardResponse of(EvaluateRewardCommand.Output output) {
        return new EvaluateRewardResponse(
                output.isWin(),
                output.rewardAmount()
        );
    }
}

