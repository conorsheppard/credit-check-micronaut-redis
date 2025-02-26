package com.micronaut.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.regex.Pattern;

@Data
@Serdeable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Person {
    @JsonProperty("email")
    private final String email;
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("postCode")
    private final String postCode;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static Person create(String email, String firstName, String lastName, String postCode) {
        return new Person(validateEmail(email), firstName, lastName, postCode);
    }

    private static String validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email format");

        return email;
    }
}
