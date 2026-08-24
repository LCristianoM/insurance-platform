package com.leaocrist.insurance.application.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(

        @NotBlank(message = "Name is required.")
        String name,

        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email
) {
}
