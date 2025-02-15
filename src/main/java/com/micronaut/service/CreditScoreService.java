package com.micronaut.service;

import com.micronaut.entity.CreditScore;
import com.micronaut.entity.Person;
import com.micronaut.cache.CreditScoreCache;
import com.micronaut.credit.CreditScoreProvider;
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

        CreditScore f = new CreditScore(person, scoreAverage);
        creditScoreCache.put(person.getEmail(), f);

        return Optional.of(f);
    }
}
