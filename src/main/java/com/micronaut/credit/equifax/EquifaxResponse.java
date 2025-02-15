package com.micronaut.credit.equifax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EquifaxResponse {
    private String emailAddress;
    private String firstName;
    private String surname;
    private String postCode;
    private int creditRating;
}
