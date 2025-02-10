package com.micronaut.infrastructure.cache;

import com.micronaut.core.FraudScore;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Singleton
public class SynchronizedLRUCache implements FraudScoreCache {

    private final int maxCacheSize;
    private final Map<String, FraudScore> cacheStorage;

    public SynchronizedLRUCache(@Property(name = "app.cache.size") int cacheSize) {
        this.maxCacheSize = cacheSize; // Inject cache size from config

        this.cacheStorage = Collections.synchronizedMap(new LinkedHashMap<>(maxCacheSize + 1, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, FraudScore> eldest) {
                return size() > maxCacheSize; // Ensure LRU eviction works correctly
            }
        });
    }

    public synchronized void put(String key, FraudScore value) {
        cacheStorage.put(key, value);
    }

    public synchronized Optional<FraudScore> get(String key) {
        return Optional.ofNullable(cacheStorage.get(key));
    }

    public synchronized boolean containsKey(String key) {
        return cacheStorage.containsKey(key);
    }

    public synchronized void clear() {
        cacheStorage.clear();
    }
}
