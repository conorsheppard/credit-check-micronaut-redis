package com.micronaut.credit.transunion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TransUnionResponse {

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
