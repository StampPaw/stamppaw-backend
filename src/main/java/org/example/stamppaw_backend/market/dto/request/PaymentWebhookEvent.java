package org.example.stamppaw_backend.market.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PaymentWebhookEvent {
    private String eventType;
    private Instant createdAt;
    private PaymentWebhookData data;
}
