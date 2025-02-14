package com.micronaut.core;

import com.micronaut.infrastructure.cache.CreditScoreCache;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;

@Singleton
@AllArgsConstructor
public class CreditScoreService {
    @Inject
    private final CreditScoreCache creditScoreCache;
    private final CreditScoreProvider[] creditScoreProviders;

    public Optional<CreditScore> calculateScoreForPerson(Person person) {
        return creditScoreCache.get(person.getEmail())
                .or(() -> updatedCreditScore(person));
    }

    private Optional<CreditScore> updatedCreditScore(Person person) {
        var listOfScores = Arrays
                .stream(creditScoreProviders)
                .mapToInt(f -> f.getLatestScore(person)
                        .map(CreditScore::getScore)
                        .orElse(-1))
                .filter(score -> score >= 0)
                .boxed()
                .toList();

        if (listOfScores.isEmpty()) return Optional.empty();

        var totalOfScores = listOfScores.stream().mapToInt(i -> i).sum();
        var scoreAverage = totalOfScores / creditScoreProviders.length;

        CreditScore f = new CreditScore(person, scoreAverage, LocalDateTime.now(ZoneOffset.UTC));
        creditScoreCache.put(person.getEmail(), f);

        return Optional.of(f);
    }
}
