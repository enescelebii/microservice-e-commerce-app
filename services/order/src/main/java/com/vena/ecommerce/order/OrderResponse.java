package com.vena.ecommerce.order;

import com.vena.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String customerId
) {
}
