package com.bookshop.service.impl;

import com.bookshop.dto.OrderDto;
import com.bookshop.dto.OrderItemDto;
import com.bookshop.dto.PlaceOrderDto;
import com.bookshop.dto.UpdateOrderStatusDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.OrderItemMapper;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.model.Order;
import com.bookshop.model.OrderItem;
import com.bookshop.model.ShoppingCart;
import com.bookshop.model.enums.OrderStatus;
import com.bookshop.repository.OrderItemRepository;
import com.bookshop.repository.OrderRepository;
import com.bookshop.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Override
    @Transactional
    public Page<OrderDto> getAllOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId, PlaceOrderDto orderDto) {
        ShoppingCart shoppingCart = shoppingCartServiceImpl.getCartOrThrow(userId);;

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Shopping cart is empty for user with id: " + userId);
        }
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItem -> convertToOrderItem(cartItem, order))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);

        orderRepository.save(order);
        shoppingCart.getCartItems().clear();

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(UpdateOrderStatusDto updateOrderStatus, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Order with id " + orderId + " not found"));

        order.setStatus(updateOrderStatus.getStatus());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public Page<OrderItemDto> getOrderItems(Long orderId, Pageable pageable) {
        return orderItemRepository.findByOrderId(orderId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional
    public OrderItemDto getOrderItem(Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(
                "Order with id " + orderId + " not found"));
        OrderItem orderItem = orderItemRepository.getByOrderAndId(order, orderItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrderItem with id " + orderItemId + " not found"));

        return orderItemMapper.toDto(orderItem);
    }

    private OrderItem convertToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());

        return orderItem;
    }
}
