package com.micronaut.core;

import java.util.Optional;

public interface FraudScoreRepository {

    Optional<FraudScore> getScore(Person person);

    void saveScore(FraudScore score);
}
