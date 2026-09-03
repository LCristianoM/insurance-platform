package com.leaocrist.insurance.application.policy;

public class PolicyNumberAlreadyExistsException extends RuntimeException{

    public PolicyNumberAlreadyExistsException(String policyNumber){
        super("Policy Number already exists: " + policyNumber);
    }
}
