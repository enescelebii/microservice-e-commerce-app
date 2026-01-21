package com.vena.notification.kafka.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        String paymentMethod,
        Customer customer,
        List<Product> products
) {
}
