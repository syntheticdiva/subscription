package com.java.subscription.exception;

public class InvalidSubscriptionDataException extends RuntimeException {
    public InvalidSubscriptionDataException(String message) {
        super(message);
    }
}