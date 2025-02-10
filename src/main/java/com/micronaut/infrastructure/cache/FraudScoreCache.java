package com.micronaut.infrastructure.cache;

import com.micronaut.core.FraudScore;

import java.util.Optional;

public interface FraudScoreCache {
    Optional<FraudScore> get(String key);

    void put(String key, FraudScore value);
}
