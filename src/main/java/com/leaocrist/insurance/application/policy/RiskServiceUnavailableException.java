package com.leaocrist.insurance.application.policy;

public class RiskServiceUnavailableException extends RuntimeException {
    public RiskServiceUnavailableException(String message) {
        super(
                "Risk service not unavailable."
        );
    }
}
