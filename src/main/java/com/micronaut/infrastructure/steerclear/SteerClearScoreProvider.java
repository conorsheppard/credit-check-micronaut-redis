package com.micronaut.infrastructure.steerclear;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreProvider;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.steerclear.SteerClearResponse.RiskGroup;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class SteerClearScoreProvider implements FraudScoreProvider {

    private final SteerClearClient steerClearClient;

    public SteerClearScoreProvider(SteerClearClient steerClearClient) {
        this.steerClearClient = steerClearClient;
    }

    @Override
    public Optional<FraudScore> getLatestScore(Person person) {
        SteerClearRequest steerClearRequest = new SteerClearRequest(person.getEmail(), person.getFirstName(),
            person.getLastName(), person.getPostCode());

        SteerClearResponse steerClearResponse = steerClearClient.getScore(steerClearRequest);
        if (steerClearResponse == null) {
            return Optional.empty();
        }

        return Optional.of(mapToFraudScore(steerClearResponse));
    }

    private FraudScore mapToFraudScore(SteerClearResponse steerClearResponse) {
        return FraudScore.builder()
            .person(Person.create(steerClearResponse.getEmail(),
                    steerClearResponse.getFirstName(),
                    steerClearResponse.getLastName(),
                    steerClearResponse.getPostCode()))
            .score(mapToScore(steerClearResponse.getRiskGroup()))
            .build();
    }

    private int mapToScore(RiskGroup riskGroup) {
        return riskGroup.ordinal();
    }
}
