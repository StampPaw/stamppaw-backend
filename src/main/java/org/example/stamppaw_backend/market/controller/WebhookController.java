package org.example.stamppaw_backend.market.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stamppaw_backend.market.dto.request.PaymentWebhookEvent;
import org.example.stamppaw_backend.market.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final PaymentService paymentService;

    @PostMapping("/toss/payment")
    public ResponseEntity<Void> handlePaymentWebhook(@RequestBody PaymentWebhookEvent event) {
        try {
            log.info("Webhook 요청 수신: eventType={}, orderId={}",
                    event.getEventType(), event.getData().getOrderId());

            if ("PAYMENT_STATUS_CHANGED".equals(event.getEventType())) {
                paymentService.updatePaymentStatus(event.getData());
            }

            return ResponseEntity.ok().build();
        } catch(Exception e) {
            log.error("Webhook 처리 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
