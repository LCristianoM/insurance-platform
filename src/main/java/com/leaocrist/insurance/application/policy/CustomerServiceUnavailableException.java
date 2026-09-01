package com.leaocrist.insurance.application.policy;

public class CustomerServiceUnavailableException extends RuntimeException {
    public CustomerServiceUnavailableException(String message) {
        super(
                "Customer service is unavailable."
        );
    }
}
