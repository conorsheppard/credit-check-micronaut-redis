package com.micronaut.credit.equifax;

public interface EquifaxClient {
    EquifaxResponse getScore(EquifaxRequest request);
}
