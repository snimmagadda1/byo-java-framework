package com.github.snimmagadda1.framework;

import com.github.snimmagadda1.framework.annotation.Transactional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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
    if (isTransactional(method)) {
      return handleTransaction(method, args);
    }
    return method.invoke(proxiedObject, args);
  }

  // Method is the method on interface, so we must get on the class
  // to check if it has the annotation
  private boolean isTransactional(Method method) {
    try {
      return proxiedObject
          .getClass()
          .getMethod(method.getName(), method.getParameterTypes())
          .isAnnotationPresent(Transactional.class);
    } catch (Exception e) {
      return false;
    }
  }

  private Object handleTransaction(Method method, Object[] args)
      throws IllegalAccessException, InvocationTargetException {
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
