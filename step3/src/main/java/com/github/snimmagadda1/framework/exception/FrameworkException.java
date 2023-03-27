package com.github.snimmagadda1.framework.exception;

public class FrameworkException extends RuntimeException {

    private String message;

    public FrameworkException(String message) {
        super(message);
    }
    
    public FrameworkException(Throwable throwable) {
        super("Unkown exception", throwable);
    }  
}
