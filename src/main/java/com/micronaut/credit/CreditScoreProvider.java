package com.micronaut.credit;

import com.micronaut.entity.CreditScore;
import com.micronaut.entity.Person;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public interface CreditScoreProvider {
    Optional<CreditScore> getLatestScore(Person person);
}
