package com.bookshop.repository;

import com.bookshop.model.Order;
import com.bookshop.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> getByOrderAndId(Order order, Long orderItemId);
}
