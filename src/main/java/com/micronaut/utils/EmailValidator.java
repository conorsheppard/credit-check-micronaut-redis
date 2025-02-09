package com.micronaut.utils;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile(".+@.+");

    public static String validateEmail(String email) throws IllegalArgumentException {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        } else {
            if (!VALID_EMAIL_PATTERN.matcher(email).matches()) {
                throw new IllegalArgumentException("Invalid email format.");
            }
        }
        return email;
    }
}
