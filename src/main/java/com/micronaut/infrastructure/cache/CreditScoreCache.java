package com.micronaut.infrastructure.cache;

import com.micronaut.core.CreditScore;

import java.util.Optional;

public interface CreditScoreCache {
    Optional<CreditScore> get(String key);

    void put(String key, CreditScore value);
}
