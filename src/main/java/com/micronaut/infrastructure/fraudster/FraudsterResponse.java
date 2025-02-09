package com.micronaut.infrastructure.fraudster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FraudsterResponse {
    private String emailAddress;
    private String firstName;
    private String surname;
    private String postCode;
    private int fraudRating;
}
