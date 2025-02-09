package com.micronaut.infrastructure.fraudster;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreProvider;
import com.micronaut.core.Person;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class FraudsterScoreProvider implements FraudScoreProvider {

    private final FraudsterClient fraudsterClient;

    public FraudsterScoreProvider(FraudsterClient fraudsterClient) {
        this.fraudsterClient = fraudsterClient;
    }

    @Override
    public Optional<FraudScore> getLatestScore(Person person) {
        FraudsterRequest fraudsterRequest = FraudsterRequest.fromPerson(person);

        FraudsterResponse fraudsterResponse = fraudsterClient.getScore(fraudsterRequest);

        if (fraudsterResponse == null) return Optional.empty();

        return Optional.of(FraudScore.builder()
            .person(Person.create(fraudsterResponse.getEmailAddress(),
                                  fraudsterResponse.getFirstName(),
                                  fraudsterResponse.getSurname(),
                                  fraudsterResponse.getPostCode()))
            .score(mapToScore(fraudsterResponse.getFraudRating()))
            .build());
    }

    private int mapToScore(int fraudRating) {
        return fraudRating;
    }
}
