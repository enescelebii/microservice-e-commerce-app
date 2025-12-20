package com.vena.ecommerce.service;


import com.vena.ecommerce.DTO.CustomerResponse;
import com.vena.ecommerce.repository.CustomerRepository;
import com.vena.ecommerce.DTO.CustomerRequest;
import com.vena.ecommerce.customer.Customer;
import com.vena.ecommerce.exception.CustomerNotFoundException;
import com.vena.ecommerce.mapper.CustomerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot update customer:: No customer found with id %s", request.id())));
        mergeCustomer(customer, request);
        repository.save(customer);

    }
    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    public Boolean existById(String customerId) {
        return repository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with id %s", customerId)));

    }

    public void deleteCustomer(String customerId) {
        if (!repository.existsById(customerId)) {
            throw new CustomerNotFoundException(String.format("Cannot delete customer:: No customer found with id %s", customerId));
        }
        repository.deleteById(customerId);
    }
}
