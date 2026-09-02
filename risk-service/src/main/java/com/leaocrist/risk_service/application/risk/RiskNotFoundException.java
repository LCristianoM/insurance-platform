package com.leaocrist.risk_service.application.risk;

public class RiskNotFoundException extends RuntimeException{
    public RiskNotFoundException(Long id){
        super("Risk not found with id: " + id);
    }
}
