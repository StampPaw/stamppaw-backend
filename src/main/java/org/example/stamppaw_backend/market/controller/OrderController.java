package org.example.stamppaw_backend.market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.market.dto.request.OrderCreateRequest;
import org.example.stamppaw_backend.market.dto.response.OrderResponse;
import org.example.stamppaw_backend.market.entity.Order;
import org.example.stamppaw_backend.market.service.OrderService;
import org.example.stamppaw_backend.user.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid OrderCreateRequest request
    ) {
        Order order = orderService.createOrder(userDetails.getUser().getId(), request);
        return ResponseEntity.ok(OrderResponse.fromEntity(order));
    }
}
