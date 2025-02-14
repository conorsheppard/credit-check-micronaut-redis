package com.micronaut.response;

import com.micronaut.core.CreditScore;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Serdeable
public class CreditScoreResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String postCode;
    private int creditScore;

    public static CreditScoreResponse fromCreditScore(CreditScore creditScore) {
        return new CreditScoreResponse(
            creditScore.getPerson().getEmail(),
            creditScore.getPerson().getFirstName(),
            creditScore.getPerson().getLastName(),
            creditScore.getPerson().getPostCode(),
            creditScore.getScore());
    }
}
