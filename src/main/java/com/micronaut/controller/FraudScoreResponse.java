package com.micronaut.controller;

import com.micronaut.core.FraudScore;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Serdeable
public class FraudScoreResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String postCode;
    private int score;

    public static FraudScoreResponse fromFraudScore(FraudScore fraudScore) {
        return new FraudScoreResponse(
            fraudScore.getPerson().getEmail(),
            fraudScore.getPerson().getFirstName(),
            fraudScore.getPerson().getLastName(),
            fraudScore.getPerson().getPostCode(),
            fraudScore.getScore());
    }
}
