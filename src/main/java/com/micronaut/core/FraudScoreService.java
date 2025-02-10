package com.micronaut.core;

import com.micronaut.infrastructure.cache.FraudScoreCache;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;

@Singleton
@AllArgsConstructor
public class FraudScoreService {
    @Inject
    private final FraudScoreCache fraudScoreCache;
    private final FraudScoreProvider[] fraudScoreProviders;

    public Optional<FraudScore> calculateScoreForPerson(Person person) {
        return fraudScoreCache.get(person.getEmail())
                .or(() -> updatedFraudScore(person));
    }

    private Optional<FraudScore> updatedFraudScore(Person person) {
        var listOfScores = Arrays
                .stream(fraudScoreProviders)
                .mapToInt(f -> f.getLatestScore(person)
                        .map(FraudScore::getScore)
                        .orElse(-1))
                .filter(score -> score >= 0)
                .boxed()
                .toList();

        if (listOfScores.isEmpty()) return Optional.empty();

        var totalOfScores = listOfScores.stream().mapToInt(i -> i).sum();
        var scoreAverage = totalOfScores / fraudScoreProviders.length;

        FraudScore f = new FraudScore(person, scoreAverage, LocalDateTime.now(ZoneOffset.UTC));
        fraudScoreCache.put(person.getEmail(), f);

        return Optional.of(f);
    }
}
