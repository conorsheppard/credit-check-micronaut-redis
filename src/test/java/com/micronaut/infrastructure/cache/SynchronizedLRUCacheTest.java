package com.micronaut.infrastructure.cache;

import com.micronaut.core.CreditScore;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.cache.hashmap.SynchronizedLRUCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedLRUCacheTest {

    @Test
    public void testPut() {
        CreditScoreCache creditScoreCache = new SynchronizedLRUCache(2);
        creditScoreCache.put("key", new CreditScore(Person.create(
                "email@email.com",
                "name",
                "surname",
                "NW1 8AB"),
                1, null));

        assertEquals(1, creditScoreCache.get("key")
                .map(CreditScore::getScore).orElse(-1).intValue());
    }
}
