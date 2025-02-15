package com.micronaut.credit.equifax;

import com.micronaut.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EquifaxRequest {
    private String emailAddress;
    private String firstName;
    private String surname;
    private String postCode;

    public static EquifaxRequest fromPerson(Person person) {
        return new EquifaxRequest(person.getEmail(), person.getFirstName(),
            person.getLastName(), person.getPostCode());
    }
}
