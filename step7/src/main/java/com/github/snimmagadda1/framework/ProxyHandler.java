package com.github.snimmagadda1.framework;

import com.github.snimmagadda1.framework.exception.FrameworkException;
import com.github.snimmagadda1.framework.handler.CacheHandler;
import com.github.snimmagadda1.framework.handler.TransactionHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ProxyHandler implements InvocationHandler {
  private static final Logger logger = Logger.getLogger(ProxyHandler.class.getName());
  private final Object proxiedObject;
  private final CacheHandler cacheHandler;
  private final TransactionHandler transactionHandler;

  public ProxyHandler(Object proxiedObject) {
    this.proxiedObject = proxiedObject;
    this.cacheHandler = new CacheHandler(proxiedObject);
    this.transactionHandler = new TransactionHandler(proxiedObject);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (cacheHandler.isHandled(method)) {
      logger.info("Getting from cache or computing first time");
      return cacheHandler.takeResultOrCalculate(method, args, () -> calculateResult(method, args));
    }

    return calculateResult(method, args);
  }

  private Object calculateResult(final Method method, final Object[] args) {
    if (transactionHandler.isHandled(method)) {
      logger.info("Running within transaction");
      return transactionHandler.handleTransaction(() -> invokeMethod(method, args));
    }
    return invokeMethod(method, args);
  }

  private Object invokeMethod(final Method method, final Object[] args) {
    try {
      return method.invoke(proxiedObject, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new FrameworkException(e);
    }
  }
}
