package com.micronaut.infrastructure.fraudster;

import com.micronaut.core.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FraudsterRequest {
    private String emailAddress;
    private String firstName;
    private String surname;
    private String postCode;

    public static FraudsterRequest fromPerson(Person person) {
        return new FraudsterRequest(person.getEmail(), person.getFirstName(),
            person.getLastName(), person.getPostCode());
    }
}
