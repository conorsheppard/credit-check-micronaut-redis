package com.micronaut.request;

import com.micronaut.entity.Person;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
@Serdeable
public class CreditScoreRequest {

    @QueryValue("email")
    private String email;

    @QueryValue("firstName")
    private String firstName;

    @QueryValue("lastName")
    private String lastName;

    @QueryValue("postCode")
    private String postCode;

    public Person asPerson() {
        return Person.create(email, firstName, lastName, postCode);
    }
}
