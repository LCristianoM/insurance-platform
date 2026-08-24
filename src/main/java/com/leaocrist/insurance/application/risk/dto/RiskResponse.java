package com.leaocrist.insurance.application.risk.dto;

public record RiskResponse(
        Long id,
        String type,
        String description
) {
}
