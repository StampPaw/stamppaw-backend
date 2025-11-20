package org.example.stamppaw_backend.market.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {

    private Long orderId;

    private BigDecimal amount;
    private String orderName;
}
