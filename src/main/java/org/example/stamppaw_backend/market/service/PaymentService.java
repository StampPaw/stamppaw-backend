package org.example.stamppaw_backend.market.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stamppaw_backend.market.dto.request.PaymentRequest;
import org.example.stamppaw_backend.market.dto.request.PaymentWebhookData;
import org.example.stamppaw_backend.market.dto.response.TossPaymentResponse;
import org.example.stamppaw_backend.market.entity.Payment;
import org.example.stamppaw_backend.market.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final TossPaymentsApiClient tossPaymentsApiClient;

    public Payment preparePayment(PaymentRequest request) {
        String tossOrderId = generateOrderId();

        Payment payment = Payment.create(
                tossOrderId,
                request.getAmount(),
                request.getOrderName()
        );

        Payment savedPayment = paymentRepository.save(payment);
        log.info("결제 준비 완료: orderId={}, amount={}", tossOrderId, request.getAmount());

        return savedPayment;
    }

    public Payment confirmPayment(String paymentKey, String orderId, Long amount) {
        Payment payment = paymentRepository.findByTossOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다: " + orderId));

        BigDecimal requestAmount = BigDecimal.valueOf(amount);
        if(payment.getAmount().compareTo(requestAmount) != 0) {
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }

        try {
            TossPaymentResponse tossResponse = tossPaymentsApiClient.confirmPayment(paymentKey, orderId, amount);

            payment.approve(
                    tossResponse.getPaymentKey(),
                    tossResponse.getMethod(),
                    tossResponse.getReceipt() != null ? tossResponse.getReceipt().getUrl() : null
            );

            Payment savedPayment = paymentRepository.save(payment);
            log.info("결제 승인 완료: paymentKey={}, orderId={}", paymentKey, orderId);

            return savedPayment;
        } catch (Exception e) {
            log.error("결제 승인 실패: paymentKey={}, error={}", paymentKey, e.getMessage());
            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getMessage());
        }
    }

    //결제 상태 변경
    public void updatePaymentStatus(PaymentWebhookData webhookData) {
        Payment payment = paymentRepository.findByTossOrderId(webhookData.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다: " + webhookData.getOrderId()));

        log.info("Webhook을 통한 결제 상태 업데이드: orderId={}, status={}", webhookData.getOrderId(), webhookData.getStatus());

        if("DONE".equals(webhookData.getStatus())) {
            payment.approve(
                    webhookData.getPaymentKey(),
                    webhookData.getMethod(),
                    null
            );
        } else if ("CANCELED".equals(webhookData.getStatus())) {
            payment.cancel("웹훅을 통한 취소");
        }

        paymentRepository.save(payment);
        log.info("결제 상태 업데이트 완료: orderId={}, status={}", webhookData.getOrderId(), webhookData.getStatus());
    }

    private String generateOrderId() {
        return "ORDER_" + UUID.randomUUID().toString().substring(0, 16).toUpperCase();
    }
}
