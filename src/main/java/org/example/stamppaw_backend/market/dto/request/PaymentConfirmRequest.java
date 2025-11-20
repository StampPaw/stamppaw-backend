package org.example.stamppaw_backend.market.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmRequest {
    private String paymentKey;
    private String orderId; // tossOrderId
    private Long amount;
}
