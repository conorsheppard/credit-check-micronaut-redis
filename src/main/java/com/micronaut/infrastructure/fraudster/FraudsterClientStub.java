package com.micronaut.infrastructure.fraudster;

import jakarta.inject.Singleton;

import java.util.Random;

@Singleton
public class FraudsterClientStub implements FraudsterClient {
    private static final int UPPER_SCORE_BOUND = 100;

    private final Random random = new Random(System.currentTimeMillis());

    public FraudsterResponse getScore(FraudsterRequest request) {
        return new FraudsterResponse(
            request.getEmailAddress(),
            request.getFirstName(),
            request.getSurname(),
            request.getPostCode(),
            randomFraudRating()
        );
    }

    private int randomFraudRating() {
        return random.nextInt(UPPER_SCORE_BOUND);
    }
}
