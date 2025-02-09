package com.micronaut.infrastructure.steerclear;

public interface SteerClearClient {
    SteerClearResponse getScore(SteerClearRequest request);
}
