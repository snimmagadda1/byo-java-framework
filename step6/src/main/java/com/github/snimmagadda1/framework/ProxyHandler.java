package com.github.snimmagadda1.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.snimmagadda1.framework.annotation.Cacheable;
import com.github.snimmagadda1.framework.annotation.Transactional;

public class ProxyHandler implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(ProxyHandler.class.getName());
    private final Object proxiedObject;
    private final Map<List<Object>, Object> cacheContainer = new HashMap<>();

    public ProxyHandler(Object proxiedObject) {
        this.proxiedObject = proxiedObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isCacheable(method)) {
            Object result = cacheContainer.get(createCacheKey(method, args));
            if (result != null) {
                logger.info("Returning result from cache");
                return result;
            }
        }

        
        if (isTransactional(method)) {
            return handleTransaction(method, args);
        }
        return calculateResult(method, args);
    }

    private List<Object> createCacheKey(Method method, Object[] args) {
        return List.of(method, Arrays.asList(args));
    }

    private Object calculateResult(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        final Object result = method.invoke(proxiedObject, args);
        if (isCacheable(method)) {
            logger.info("Caching result");
            cacheContainer.put(createCacheKey(method, args), result);
        }
        return result;
    }

    private boolean isCacheable(Method method) {
        try {
            return proxiedObject.getClass().getMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Cacheable.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    // Method is the method on interface, so we must get on the class 
    // to check if it has the annotation
    private boolean isTransactional(Method method) {
        try{
            return proxiedObject.getClass().getMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Transactional.class);
        } catch (Exception e) {
            return false;
        }
    }

    private Object handleTransaction(Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        try {
            beginTransaction();
            final Object invoke = calculateResult(method, args);
            commitTransaction();
            return invoke;
        } catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }

    
    private void beginTransaction() {
        logger.info("BEGIN TRANSACTION");
    }

    private void commitTransaction() {
        logger.info("COMMIT TRANSACTION");
    }

    private void rollbackTransaction() {
        logger.info("ROLLBACK TRANSACTION");
    }

}
