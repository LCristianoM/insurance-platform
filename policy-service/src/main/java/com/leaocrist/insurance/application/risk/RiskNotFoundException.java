package com.leaocrist.insurance.application.risk;

public class RiskNotFoundException extends RuntimeException{
    public RiskNotFoundException(Long id){
        super("Risk not found with id: " + id);
    }
}
