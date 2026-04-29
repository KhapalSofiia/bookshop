package com.bookshop.service;

import com.bookshop.dto.OrderDto;
import com.bookshop.dto.OrderItemDto;
import com.bookshop.dto.PlaceOrderDto;
import com.bookshop.dto.UpdateOrderStatusDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders(Long userId);

    OrderDto placeOrder(Long userId, PlaceOrderDto orderDto);

    OrderDto updateOrderStatus(UpdateOrderStatusDto updateOrderStatus, Long orderId);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long orderItemId);
}
