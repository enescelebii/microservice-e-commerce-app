package com.vena.order.kafka;

import com.vena.order.customer.CustomerResponse;
import com.vena.order.payment.PaymentMethod;
import com.vena.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
