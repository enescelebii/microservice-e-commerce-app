package com.vena.ecommerce.product.controller;


import com.vena.ecommerce.product.DTO.ProductPurchaseRequest;
import com.vena.ecommerce.product.DTO.ProductPurchaseResponse;
import com.vena.ecommerce.product.DTO.ProductRequest;
import com.vena.ecommerce.product.DTO.ProductResponse;
import com.vena.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody  @Valid ProductRequest Request
    ) {
        return ResponseEntity.ok(service.createProduct(Request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody @Valid List<ProductPurchaseRequest> requests
    ) {
        return ResponseEntity.ok(service.purchaseProducts(requests));
    }

    @GetMapping("/{product-id} ")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("product-id") Integer productId
    ) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
