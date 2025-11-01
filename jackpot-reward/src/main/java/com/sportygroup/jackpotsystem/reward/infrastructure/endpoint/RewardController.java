package com.sportygroup.jackpotsystem.reward.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.reward.domain.EvaluateRewardCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Rewards")
public class RewardController {

    private final EvaluateRewardCommand evaluateRewardCommand;

    @PostMapping("/public/v1/rewards/evaluate")
    @Operation(summary = "Evaluate if a bet wins the jackpot")
    public ResponseEntity<EvaluateRewardResponse> evaluateReward(@Valid @RequestBody EvaluateRewardRequest request) {
        final var input = request.toInput();
        final var output = evaluateRewardCommand.execute(input);

        return ResponseEntity.ok(EvaluateRewardResponse.of(output));
    }
}

