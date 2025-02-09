package com.micronaut.infrastructure.steerclear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SteerClearResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String postCode;
    private RiskGroup riskGroup;

    public enum RiskGroup {
        A,
        B,
        C,
        D,
        E,
        F
    }
}
