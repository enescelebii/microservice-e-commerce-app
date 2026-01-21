package com.vena.order.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {
        log.info("Sending order confirmation: {}", orderConfirmation);
        kafkaTemplate.send("order-topic", orderConfirmation);
    }
}
