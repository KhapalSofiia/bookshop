package com.bookshop.service;

import com.bookshop.dto.OrderDto;
import com.bookshop.dto.OrderItemDto;
import com.bookshop.dto.PlaceOrderDto;
import com.bookshop.dto.UpdateOrderStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getAllOrders(Long userId, Pageable pageable);

    OrderDto placeOrder(Long userId, PlaceOrderDto orderDto);

    OrderDto updateOrderStatus(UpdateOrderStatusDto updateOrderStatus, Long orderId);

    Page<OrderItemDto> getOrderItems(Long orderId, Pageable pageable);

    OrderItemDto getOrderItem(Long orderId, Long orderItemId);
}
