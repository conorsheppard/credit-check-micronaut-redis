package com.micronaut.infrastructure.steerclear;

import com.micronaut.infrastructure.steerclear.SteerClearResponse.RiskGroup;
import jakarta.inject.Singleton;

import java.util.Random;

@Singleton
public class SteerClearClientStub implements SteerClearClient {

    private final Random random = new Random(System.currentTimeMillis());

    public SteerClearResponse getScore(SteerClearRequest request) {
        return new SteerClearResponse(
            request.getEmail(),
            request.getFirstName(),
            request.getLastName(),
            request.getPostCode(),
            randomRiskGroup());
    }

    private RiskGroup randomRiskGroup() {
        int randomRiskGroupIndex = random.nextInt(RiskGroup.values().length);
        return RiskGroup.values()[randomRiskGroupIndex];
    }
}
