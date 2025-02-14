package com.micronaut.infrastructure.transunion;

import com.micronaut.core.CreditScore;
import com.micronaut.core.CreditScoreProvider;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.transunion.TransUnionResponse.RiskGroup;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Singleton
@AllArgsConstructor
public class TransUnionScoreProvider implements CreditScoreProvider {
    private final TransUnionClient transUnionClient;

    @Override
    public Optional<CreditScore> getLatestScore(Person person) {
        TransUnionRequest transUnionRequest = new TransUnionRequest(person.getEmail(), person.getFirstName(),
            person.getLastName(), person.getPostCode());

        TransUnionResponse transUnionResponse = transUnionClient.getScore(transUnionRequest);
        if (transUnionResponse == null) {
            return Optional.empty();
        }

        return Optional.of(mapToCreditScore(transUnionResponse));
    }

    private CreditScore mapToCreditScore(TransUnionResponse transUnionResponse) {
        return CreditScore.builder()
            .person(Person.create(transUnionResponse.getEmail(),
                    transUnionResponse.getFirstName(),
                    transUnionResponse.getLastName(),
                    transUnionResponse.getPostCode()))
            .score(mapToScore(transUnionResponse.getRiskGroup()))
            .build();
    }

    private int mapToScore(RiskGroup riskGroup) {
        return riskGroup.ordinal();
    }
}
