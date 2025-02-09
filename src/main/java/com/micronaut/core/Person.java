package com.micronaut.core;

import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.regex.Pattern;

@Data
@Serdeable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Person {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String postCode;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static Person create(String email, String firstName, String lastName, String postCode) {
        return new Person(validateEmail(email), firstName, lastName, postCode);
    }

    private static String validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email format");

        return email;
    }
}
