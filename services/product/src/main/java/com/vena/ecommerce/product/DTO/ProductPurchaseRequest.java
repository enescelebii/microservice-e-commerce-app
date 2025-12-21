package com.vena.ecommerce.product.DTO;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is Mandatory")
        Integer productId,
        @NotNull(message = "Quantity is Mandatory")
        double quantity
) {
}
