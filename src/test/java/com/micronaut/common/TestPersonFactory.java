package com.micronaut.common;

import com.micronaut.entity.Person;
import java.util.Random;

public class TestPersonFactory {

    private static final Random random = new Random(System.currentTimeMillis());
    private static final String[] firstNames = {"Seamus", "Tadhg", "Cormac", "Ciarán", "Siobhán", "Pádraig", "Aoibheann"};
    private static final String[] lastNames = {"McCarthy", "Murphy", "O'Connor", "O'Sullivan", "Doyle", "Walsh", "Ó Ceallaigh"};

    public static Person getTestPerson() {
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        return Person.builder()
            .firstName(randomFirstName)
            .lastName(randomLastName)
            .email(randomFirstName.toLowerCase() + "." + randomLastName.toLowerCase() + "@example.com")
            .postCode("NW1 " + random.nextInt(1000))
            .build();
    }
}
