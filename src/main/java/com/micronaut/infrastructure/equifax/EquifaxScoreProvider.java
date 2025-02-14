package com.micronaut.infrastructure.equifax;

import com.micronaut.core.CreditScore;
import com.micronaut.core.CreditScoreProvider;
import com.micronaut.core.Person;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Singleton
@AllArgsConstructor
public class EquifaxScoreProvider implements CreditScoreProvider {
    private final EquifaxClient equifaxClient;

    @Override
    public Optional<CreditScore> getLatestScore(Person person) {
        EquifaxRequest creditRequest = EquifaxRequest.fromPerson(person);

        EquifaxResponse equifaxResponse = equifaxClient.getScore(creditRequest);

        if (equifaxResponse == null) return Optional.empty();

        return Optional.of(CreditScore.builder()
            .person(Person.create(equifaxResponse.getEmailAddress(),
                                  equifaxResponse.getFirstName(),
                                  equifaxResponse.getSurname(),
                                  equifaxResponse.getPostCode()))
            .score(mapToScore(equifaxResponse.getCreditRating()))
            .build());
    }

    private int mapToScore(int creditRating) {
        return creditRating;
    }
}
