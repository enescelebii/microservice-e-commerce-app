package com.vena.ecommerce.DTO;

import com.vena.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer FirstName is required")
        String firstName,
        @NotNull(message = "Customer LastName is required")
        String lastName,
        @NotNull(message = "Customer Email is required")
        @Email(message = "Invalid email format")
        String email,
        Address address
) {
}
