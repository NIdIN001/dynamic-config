package com.company.exception;

public class DynamicConfigException extends Exception {
    public DynamicConfigException(String message) {
        super(message);
    }

    public DynamicConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
