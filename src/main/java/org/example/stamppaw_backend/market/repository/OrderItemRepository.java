package org.example.stamppaw_backend.market.repository;

import org.example.stamppaw_backend.market.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    @Query("""
        select oi from OrderItem oi
        left join fetch oi.product
        where oi.order.id in :orderIds
        """)
    List<OrderItem> findItemsByOrderIds(List<Long> orderIds);
}
