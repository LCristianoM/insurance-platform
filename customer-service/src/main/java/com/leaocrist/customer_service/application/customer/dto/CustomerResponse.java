package com.leaocrist.customer_service.application.customer.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phone,
        String email
) {
}
