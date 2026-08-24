package com.leaocrist.insurance.application.policy.dto;

import com.leaocrist.insurance.domain.policy.PolicyTerm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PolicyRequest(

        @NotBlank(message = "Policy number is required.")
        String policyNumber,

        @NotBlank(message = "Policy type is required.")
        String policyType,

        @NotNull(message = "Effective date is required.")
        LocalDate effectiveDate,

        @NotNull(message = "Policy term is required.")
        PolicyTerm term,

        @NotNull(message = "Customer ID is required.")
        Long customerId,

        @NotNull(message = "Risk ID is required.")
        Long riskId
) {
}
