package com.vena.ecommerce.controller;


import com.vena.ecommerce.DTO.CustomerRequest;
import com.vena.ecommerce.DTO.CustomerResponse;
import com.vena.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        // Implementation for creating a customer
        return ResponseEntity.ok(service.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        service.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCustomers());
    }


    @GetMapping("/exits/{customer-Id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("customer-Id") String customerId) {
        return ResponseEntity.ok(service.existById(customerId));
    }

    @GetMapping("/{customer-Id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-Id") String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customer-Id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-Id") String customerId) {
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }

}
