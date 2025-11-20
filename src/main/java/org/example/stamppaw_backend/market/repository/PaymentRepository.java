package org.example.stamppaw_backend.market.repository;

import org.example.stamppaw_backend.market.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTossOrderId(String tossOrderId);

    Optional<Payment> findByPaymentKey(String paymentKey);
}
