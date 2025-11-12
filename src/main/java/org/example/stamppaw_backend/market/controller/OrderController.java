package org.example.stamppaw_backend.market.controller;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.market.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


}
