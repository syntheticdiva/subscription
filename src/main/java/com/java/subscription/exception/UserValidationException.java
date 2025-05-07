package com.java.subscription.exception;

import java.util.HashMap;
import java.util.Map;

public class UserValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public UserValidationException(String field, String errorMessage) {
        super("User validation error");
        this.errors = new HashMap<>();
        this.errors.put(field, errorMessage);
    }

    public UserValidationException(Map<String, String> errors) {
        super("User validations error");
        this.errors = new HashMap<>(errors);
    }

    public Map<String, String> getErrors() {
        return new HashMap<>(errors);
    }
}
