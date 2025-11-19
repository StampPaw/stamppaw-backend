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

    /**
     * 결제 준비 (Checkout) - 프론트엔드에서 결제 버튼 누르면 이 API 호출
     */
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

    /**
     * 결제 성공 시 호출되는 API - Toss 결제 완료 후 프론트엔드에서 이 엔드포인트로 redirect 또는 API 요청
     */
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

    /**
     * 결제 실패 처리
     */
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

