package com.micronaut.credit.cache.hashmap;

import com.micronaut.cache.CreditScoreCache;
import com.micronaut.common.TestPersonFactory;
import com.micronaut.entity.CreditScore;
import com.micronaut.entity.Person;
import com.micronaut.cache.hashmap.SynchronizedLRUCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SynchronizedLRUCacheTest {

    @Test
    public void testPut() {
        CreditScoreCache creditScoreCache = new SynchronizedLRUCache(2);
        creditScoreCache.put("key", new CreditScore(TestPersonFactory.getTestPerson(), 1));

        assertEquals(1, creditScoreCache.get("key")
                .map(CreditScore::getScore).orElse(-1).intValue());
    }

    @Test
    public void testPutFailsFor0CapCache() {
        CreditScoreCache creditScoreCache = new SynchronizedLRUCache(0);
        creditScoreCache.put("key", new CreditScore(TestPersonFactory.getTestPerson(), 1));

        assertTrue(creditScoreCache.get("key").isEmpty());
    }

    @Test
    public void testLRUEvictionPolicy() {
        CreditScoreCache creditScoreCache = new SynchronizedLRUCache(1);
        creditScoreCache.put("key1", new CreditScore(TestPersonFactory.getTestPerson(), 1));
        creditScoreCache.put("key2", new CreditScore(TestPersonFactory.getTestPerson(), 100));

        assertTrue(creditScoreCache.get("key1").isEmpty());
    }
}
