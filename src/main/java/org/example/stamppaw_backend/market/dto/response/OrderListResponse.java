package org.example.stamppaw_backend.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.stamppaw_backend.market.repository.projection.OrderListRow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private String status;
    private BigDecimal shippingFee;
    private String shippingName;
    private String shippingMobile;
    private String shippingAddress;
    private String shippingStatus;
    private LocalDateTime registeredAt;
    private String username;

    public static OrderListResponse fromProjection(OrderListRow row) {
        return OrderListResponse.builder()
                .orderId(row.getOrderId())
                .totalAmount(row.getTotalAmount())
                .status(row.getStatus())
                .shippingFee(row.getshippingFee())
                .shippingName(row.getshippingName())
                .shippingMobile(row.getShippingMobile())
                .shippingAddress(row.getShippingAddress())
                .shippingStatus(row.getShippingStatus())
                .registeredAt(row.getRegisteredAt())
                .username(row.getUsername()) //상세보기 조회로 이동
                .build();
    }
}
