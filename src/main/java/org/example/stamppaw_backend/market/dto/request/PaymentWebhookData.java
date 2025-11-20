package org.example.stamppaw_backend.market.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentWebhookData {
    @JsonProperty("paymentKey")
    private String paymentKey;

    // Toss에 보낸 orderId = tossOrderId (String)
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("method")
    private String method;

}
