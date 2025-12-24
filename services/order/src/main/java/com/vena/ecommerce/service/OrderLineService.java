package com.vena.ecommerce.service;


import com.vena.ecommerce.orderLine.OrderLineRequest;
import com.vena.ecommerce.orderLine.OrderLineMapper;
import com.vena.ecommerce.orderLine.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = mapper.toOrderLine(orderLineRequest);
        return repository.save(order).getId();
    }
}
