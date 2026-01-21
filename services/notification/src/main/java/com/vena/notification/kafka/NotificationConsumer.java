package com.vena.notification.kafka;


import com.vena.notification.email.EmailService;
import com.vena.notification.kafka.order.OrderConfirmation;
import com.vena.notification.kafka.payment.PaymentConfirmation;
import com.vena.notification.notification.Notification;
import com.vena.notification.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.vena.notification.notification.NotificationType.ORDER_CONFIRMATION;
import static com.vena.notification.notification.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic", groupId = "notification-group")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received payment confirmation <{}>", paymentConfirmation);
        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received order confirmation <{}>", orderConfirmation);
        repository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }


}
