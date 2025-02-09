package com.micronaut.core;

import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public interface FraudScoreRepository {

    Optional<FraudScore> getScore(Person person);

    void saveScore(FraudScore score);
}
