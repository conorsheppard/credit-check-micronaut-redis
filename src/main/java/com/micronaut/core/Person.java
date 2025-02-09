package com.micronaut.core;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

import static com.micronaut.utils.EmailValidator.validateEmail;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Person {
    private String email;
    private String firstName;
    private String lastName;
    private String postCode;

    public Person setEmail(String email) throws IllegalArgumentException {
        this.email = validateEmail(email);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return email.equals(person.email) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName,
            person.lastName) && Objects.equals(postCode, person.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, postCode);
    }
}
