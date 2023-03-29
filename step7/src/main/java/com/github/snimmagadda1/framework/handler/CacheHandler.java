package com.github.snimmagadda1.framework.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.github.snimmagadda1.framework.annotation.Cacheable;

public class CacheHandler extends AbstractHandler {

    private final Map<List<Object>, Object> cacheContainers = new ConcurrentHashMap<>();

    public CacheHandler(Object proxiedObject) {
        super(proxiedObject, Cacheable.class);
    }

    // Key is method & args as a list -> cool
    public List<Object> createKeyCache(final Method method, final Object[] args) {
        return List.of(method, Arrays.asList(args));
    }

    // Use supplier to avoid calculating result if it is already in cache
    // also abstract imlementation for handling elsewhere (i.e a transaction handler)
    public Object takeResultOrCalculate(final Method method, Object[] args, final Supplier<Object> resultSupplier) {
        final List<Object> keyCache = createKeyCache(method, args);
        return cacheContainers.computeIfAbsent(keyCache, key -> resultSupplier.get());
    }
    
}
