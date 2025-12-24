package com.vena.ecommerce.orderLine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        @NotNull Integer orderId,
        @NotNull Integer productId,
        @Positive Integer quantity
) {
}
