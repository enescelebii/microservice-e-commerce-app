package com.vena.ecommerce.service;

import com.vena.ecommerce.DTO.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public OrderResponse createOrder(@Valid OrderRequest request) {
        return null;
    }
}
