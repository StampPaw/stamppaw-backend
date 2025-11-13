package org.example.stamppaw_backend.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.stamppaw_backend.market.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;

    private String status;
    private BigDecimal totalAmount;
    private String shippingName;
    private String shippingAddress;
    private String shippingMobile;
    private String shippingStatus;
    private LocalDateTime registeredAt;
    private List<OrderItemResponse> items;

    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .shippingName(order.getShippingName())
                .shippingAddress(order.getShippingAddress())
                .shippingMobile(order.getShippingMobile())
                .shippingStatus(order.getShippingStatus().name())
                .registeredAt(order.getRegisteredAt())
                .items(order.getOrderItems().stream()
                        .map(OrderItemResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
