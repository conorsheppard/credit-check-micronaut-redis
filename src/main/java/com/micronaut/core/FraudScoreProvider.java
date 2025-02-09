package com.micronaut.core;

import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public interface FraudScoreProvider {
    Optional<FraudScore> getLatestScore(Person person);
}
