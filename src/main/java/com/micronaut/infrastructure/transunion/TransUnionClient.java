package com.micronaut.infrastructure.transunion;

public interface TransUnionClient {
    TransUnionResponse getScore(TransUnionRequest request);
}
