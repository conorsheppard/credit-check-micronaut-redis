package com.micronaut.core;

import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public interface CreditScoreProvider {
    Optional<CreditScore> getLatestScore(Person person);
}
