package org.example.stamppaw_backend.market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.stamppaw_backend.common.BasicTimeEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BasicTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionSummary;

    private BigDecimal price;

    private int quantity;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "user_image_url", length = 2048)
    private String userImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
