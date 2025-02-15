package com.micronaut.cache;

import com.micronaut.entity.CreditScore;

import java.util.Optional;

public interface CreditScoreCache {
    Optional<CreditScore> get(String key);

    void put(String key, CreditScore value);
}
