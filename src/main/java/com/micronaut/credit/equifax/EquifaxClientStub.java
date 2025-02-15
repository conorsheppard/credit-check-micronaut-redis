package com.micronaut.credit.equifax;

import jakarta.inject.Singleton;

import java.util.Random;

@Singleton
public class EquifaxClientStub implements EquifaxClient {
    private static final int UPPER_SCORE_BOUND = 100;

    private final Random random = new Random(System.currentTimeMillis());

    public EquifaxResponse getScore(EquifaxRequest request) {
        return new EquifaxResponse(
            request.getEmailAddress(),
            request.getFirstName(),
            request.getSurname(),
            request.getPostCode(),
            randomCreditRating()
        );
    }

    private int randomCreditRating() {
        return random.nextInt(UPPER_SCORE_BOUND);
    }
}
