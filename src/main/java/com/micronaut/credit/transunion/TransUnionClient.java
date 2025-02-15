package com.micronaut.credit.transunion;

public interface TransUnionClient {
    TransUnionResponse getScore(TransUnionRequest request);
}
