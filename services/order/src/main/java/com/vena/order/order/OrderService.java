package com.vena.order.order;

import com.vena.order.orderLine.OrderLineRequest;
import com.vena.order.kafka.OrderConfirmation;
import com.vena.order.orderLine.OrderLineService;
import com.vena.order.payment.PaymentClient;
import com.vena.order.payment.PaymentRequest;
import com.vena.order.product.PurchaseRequest;
import com.vena.order.customer.CustomerClient;
import com.vena.order.exception.BusinessException;
import com.vena.order.kafka.OrderProducer;
import com.vena.order.product.ProductClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    /*
    * OutBox event ile DB ye Commit giden işlemlerin Kafka Tarafında Patlarsa bile tekrar Gönderimi Sağlanabilir Profesyonel bir yapı kurmak için önemli bir bakış açısı
    *
    * The OutBox event allows commits sent to the database to be resent even if they fail on the Kafka side. This is an important perspective for building a professional infrastructure.*/

    public Integer createOrder(@Valid @NotEmpty OrderRequest request) {
        var customer  = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Customer not found with id: " + request.customerId()));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        /*Send Kafka Message*/
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                        )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException ("Order not found with id: " + orderId));
    }
}
