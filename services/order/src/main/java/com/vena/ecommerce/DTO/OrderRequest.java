package com.vena.ecommerce.DTO;

import com.vena.ecommerce.enums.PaymentMethod;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
    Integer id,
    String reference,
    @Positive(message = "Amount must be positive")
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerId,
    List<PurchaseRequest> products
) {
}
