package com.java.subscription.exception;

public class SubscriptionConflictException extends RuntimeException {
    public SubscriptionConflictException(String message) {
        super(message);
    }
}