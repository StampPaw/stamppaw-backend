package org.example.stamppaw_backend.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "toss_order_id", nullable = false, unique = true)
    private String tossOrderId;

    @Column
    private String paymentKey;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String orderName; //상품명 또는 주문 제목 : Toss 필수 값

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.READY;

    @Column
    private String method;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private String receiptUrl;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    public static Payment create(String tossOrderId, BigDecimal amount, String orderName) {
        return Payment.builder()
                .tossOrderId(tossOrderId)
                .amount(amount)
                .orderName(orderName)
                .status(PaymentStatus.READY)
                .build();
    }

    public void approve(String paymentKey, String method, String receiptUrl) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = PaymentStatus.DONE;
        this.approvedAt = LocalDateTime.now();
        this.receiptUrl = receiptUrl;
    }

    public void cancel(String reason) {
        this.status = PaymentStatus.CANCELED;
    }
}
