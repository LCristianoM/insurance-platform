package com.leaocrist.insurance.application.policy.dto;

import com.leaocrist.insurance.domain.policy.PolicyStatus;
import com.leaocrist.insurance.domain.policy.PolicyTerm;

import java.time.LocalDate;

public record PolicyResponse(
        Long id,
        String policyNumber,
        String policyType,
        PolicyStatus status,
        PolicyTerm term,
        LocalDate effectiveDate,
        LocalDate expirationDate,
        Long customerId,
        Long riskId
) {
}
