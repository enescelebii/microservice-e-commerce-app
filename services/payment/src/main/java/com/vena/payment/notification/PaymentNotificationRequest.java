package com.vena.payment.notification;

import com.vena.payment.payment.PaymentMethod;
import java.math.BigDecimal;
public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
