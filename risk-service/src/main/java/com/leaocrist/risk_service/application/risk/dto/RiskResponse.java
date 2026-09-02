package com.leaocrist.risk_service.application.risk.dto;

public record RiskResponse(
        Long id,
        String type,
        String description
) {
}
