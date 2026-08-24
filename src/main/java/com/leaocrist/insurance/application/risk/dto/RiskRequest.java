package com.leaocrist.insurance.application.risk.dto;

import jakarta.validation.constraints.NotBlank;

public record RiskRequest(
        @NotBlank(message = "Risk type is required.")
        String type,
        @NotBlank(message = "Description is required.")
        String description
) {
}
