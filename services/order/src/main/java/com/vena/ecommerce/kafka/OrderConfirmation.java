package com.vena.ecommerce.kafka;

import com.vena.ecommerce.customer.CustomerResponse;
import com.vena.ecommerce.payment.PaymentMethod;
import com.vena.ecommerce.product.PurchaseResponse;

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
