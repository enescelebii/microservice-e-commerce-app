package com.vena.notification.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        String paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
