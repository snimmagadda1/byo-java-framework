package com.github.snimmagadda1.framework.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AbstractHandler {

    private Object proxiedObject;
    private Class<? extends Annotation> handledAnnotation;

    public AbstractHandler(Object proxiedObject, Class<? extends Annotation> annotation) {
        this.proxiedObject = proxiedObject;
        this.handledAnnotation = annotation;
    }

    public boolean isHandled(Method method) {
        try {
            return proxiedObject.getClass().getMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(handledAnnotation);
        } catch (Exception e) {
            return false;
        }
    }

}
