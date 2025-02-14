package com.micronaut.infrastructure.cache.hashmap;

import com.micronaut.core.CreditScore;
import com.micronaut.infrastructure.cache.CreditScoreCache;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Singleton
public class SynchronizedLRUCache implements CreditScoreCache {
    private final int maxCacheSize;
    private final Map<String, CreditScore> cacheStorage;

    public SynchronizedLRUCache(@Value("${app.cache.size:2}") int size) {
        this.maxCacheSize = size;

        this.cacheStorage = Collections.synchronizedMap(new LinkedHashMap<>(maxCacheSize + 1, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CreditScore> eldest) {
                return size() > maxCacheSize;
            }
        });
    }

    @Override
    public synchronized void put(String key, CreditScore value) {
        cacheStorage.put(key, value);
    }

    @Override
    public synchronized Optional<CreditScore> get(String key) {
        return Optional.ofNullable(cacheStorage.get(key));
    }

    public synchronized boolean containsKey(String key) {
        return cacheStorage.containsKey(key);
    }

    public synchronized void clear() {
        cacheStorage.clear();
    }
}
