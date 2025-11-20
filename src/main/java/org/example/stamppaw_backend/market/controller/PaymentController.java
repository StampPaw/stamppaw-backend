package org.example.stamppaw_backend.market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stamppaw_backend.config.TossPaymentConfig;
import org.example.stamppaw_backend.market.dto.request.PaymentRequest;
import org.example.stamppaw_backend.market.entity.Payment;
import org.example.stamppaw_backend.market.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody @Valid PaymentRequest request,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("결제 요청 데이터가 올바르지 않습니다.");
        }

        Payment payment = paymentService.preparePayment(request);

        return ResponseEntity.ok(Map.of(
                "payment", payment,
                "clientKey", tossPaymentConfig.getClientKey()
        ));
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount
    ) {
        try {
            Payment payment = paymentService.confirmPayment(paymentKey, orderId, amount);
            return ResponseEntity.ok(payment);

        } catch (Exception e) {
            log.error("결제 승인 실패", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "결제 승인 실패",
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/fail")
    public ResponseEntity<?> paymentFail(
            @RequestParam(required = false) String message
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "결제 실패",
                "message", message != null ? message : "결제 처리 중 오류가 발생했습니다."
        ));
    }
}

