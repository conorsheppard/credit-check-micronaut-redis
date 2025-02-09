package com.micronaut.core;

import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;

@Singleton
public class FraudScoreService {

    private final FraudScoreRepository fraudScoreRepository;
    private final FraudScoreProvider[] fraudScoreProviders;

    public FraudScoreService(FraudScoreRepository fraudScoreRepository, FraudScoreProvider... providers) {
        this.fraudScoreRepository = fraudScoreRepository;
        this.fraudScoreProviders = providers;
    }

    public Optional<FraudScore> calculateScoreForPerson(Person person) {
        return fraudScoreRepository.getScore(person)
                .or(() -> updatedFraudScore(person));
    }

    private Optional<FraudScore> updatedFraudScore(Person person) {
        var listOfScores = Arrays
                .stream(fraudScoreProviders)
                .mapToInt(f -> f.getLatestScore(person).map(FraudScore::getScore).orElse(-1))
                .filter(score -> score >= 0)
                .boxed()
                .toList();

        if (listOfScores.isEmpty()) return Optional.empty();

        var totalOfScores = listOfScores.stream().mapToInt(i -> i).sum();
        var scoreAverage = totalOfScores / fraudScoreProviders.length;

        FraudScore f = new FraudScore(person, scoreAverage, LocalDateTime.now(ZoneOffset.UTC));

        fraudScoreRepository.saveScore(f);

        return Optional.of(f);
    }
}
