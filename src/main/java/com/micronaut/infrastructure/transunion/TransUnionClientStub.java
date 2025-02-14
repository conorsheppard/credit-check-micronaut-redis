package com.micronaut.infrastructure.transunion;

import com.micronaut.infrastructure.transunion.TransUnionResponse.RiskGroup;
import jakarta.inject.Singleton;

import java.util.Random;

@Singleton
public class TransUnionClientStub implements TransUnionClient {

    private final Random random = new Random(System.currentTimeMillis());

    public TransUnionResponse getScore(TransUnionRequest request) {
        return new TransUnionResponse(
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
