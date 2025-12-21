package com.vena.ecommerce.product.service;


import com.vena.ecommerce.category.Category;
import com.vena.ecommerce.product.DTO.ProductPurchaseResponse;
import com.vena.ecommerce.product.DTO.ProductRequest;
import com.vena.ecommerce.product.DTO.ProductResponse;
import com.vena.ecommerce.product.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, @NotNull(message = "Quantity is Mandatory") double quantity) {

        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
