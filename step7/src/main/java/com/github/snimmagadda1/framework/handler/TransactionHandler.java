package com.github.snimmagadda1.framework.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.github.snimmagadda1.framework.annotation.Transactional;

public class TransactionHandler extends AbstractHandler {
    private static final Logger logger = Logger.getLogger(TransactionHandler.class.getName());

    public TransactionHandler(Object proxiedObject) {
        super(proxiedObject, Transactional.class);
    }

    public Object handleTransaction(Supplier<Object> supplier) {
        try {
            beginTransaction();
            final Object invoke = supplier.get();
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
