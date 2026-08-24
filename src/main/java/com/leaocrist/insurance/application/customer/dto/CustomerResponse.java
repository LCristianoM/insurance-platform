package com.leaocrist.insurance.application.customer.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phone,
        String email
) {
}
