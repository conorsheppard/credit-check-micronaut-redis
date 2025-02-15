package com.micronaut.credit.cache;

import com.micronaut.cache.CreditScoreCache;
import com.micronaut.entity.CreditScore;
import com.micronaut.entity.Person;
import com.micronaut.cache.hashmap.SynchronizedLRUCache;
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
                1));

        assertEquals(1, creditScoreCache.get("key")
                .map(CreditScore::getScore).orElse(-1).intValue());
    }
}
