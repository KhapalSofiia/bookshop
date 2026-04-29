package com.bookshop.repository;

import com.bookshop.model.Order;
import com.bookshop.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
