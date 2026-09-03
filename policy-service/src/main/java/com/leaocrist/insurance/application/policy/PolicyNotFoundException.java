package com.leaocrist.insurance.application.policy;

public class PolicyNotFoundException extends RuntimeException{
    public PolicyNotFoundException(Long id){
        super("Policy not found with id: " + id);
    }

    public PolicyNotFoundException(String policyNumber){
        super("Policy not found with policy number: " + policyNumber);
    }
}
