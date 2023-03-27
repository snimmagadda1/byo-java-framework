package com.github.snimmagadda1.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ProxyHandler implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(ProxyHandler.class.getName());
    private final Object proxiedObject;

    public ProxyHandler(Object proxiedObject) {
        this.proxiedObject = proxiedObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            beginTransaction();
            final Object invoke = method.invoke(proxiedObject, args);
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
