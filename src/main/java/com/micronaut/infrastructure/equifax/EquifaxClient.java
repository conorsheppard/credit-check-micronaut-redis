package com.micronaut.infrastructure.equifax;

public interface EquifaxClient {
    EquifaxResponse getScore(EquifaxRequest request);
}
