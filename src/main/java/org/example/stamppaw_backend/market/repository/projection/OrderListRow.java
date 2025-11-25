package org.example.stamppaw_backend.market.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderListRow {
    Long getOrderId();
    BigDecimal getTotalAmount();
    String getStatus();
    BigDecimal getshippingFee();
    String getshippingName();
    String getShippingMobile();
    String getShippingAddress();
    String getShippingStatus();
    LocalDateTime getRegisteredAt();
    String getUsername();
}
