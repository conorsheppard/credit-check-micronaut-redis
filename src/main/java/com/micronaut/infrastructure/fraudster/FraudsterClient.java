package com.micronaut.infrastructure.fraudster;

public interface FraudsterClient {
    FraudsterResponse getScore(FraudsterRequest request);
}
