package com.micronaut.core;

import java.util.Optional;

public interface FraudScoreProvider {
    Optional<FraudScore> getLatestScore(Person person);
}
