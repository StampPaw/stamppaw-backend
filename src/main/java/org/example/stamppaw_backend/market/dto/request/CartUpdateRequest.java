package org.example.stamppaw_backend.market.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
public class CartUpdateRequest {
    private Long cartItemId;
    private Integer quantity;

    private BigDecimal subtotal;
    private BigDecimal extraPrice;
}
