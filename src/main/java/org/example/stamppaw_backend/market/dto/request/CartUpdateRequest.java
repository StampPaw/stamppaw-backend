package org.example.stamppaw_backend.market.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartUpdateRequest {
    private Long cartItemId;
    private Integer quantity;

    private BigDecimal subtotal;
    private BigDecimal extraPrice;
}
