package com.micronaut.controller;

import com.micronaut.core.Person;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
public class FraudScoreQuery {

    @QueryValue("email")
    private String email;

    @QueryValue("firstName")
    private String firstName;

    @QueryValue("lastName")
    private String lastName;

    @QueryValue("postCode")
    private String postCode;

    public Person asPerson() {
        return new Person()
            .setEmail(email)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPostCode(postCode);
    }
}
