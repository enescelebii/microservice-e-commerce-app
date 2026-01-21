package com.vena.order.order;

import com.vena.order.payment.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String customerId
) {
}
