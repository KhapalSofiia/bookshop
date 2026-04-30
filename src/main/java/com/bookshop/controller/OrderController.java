package com.bookshop.controller;

import com.bookshop.dto.OrderDto;
import com.bookshop.dto.OrderItemDto;
import com.bookshop.dto.PlaceOrderDto;
import com.bookshop.dto.UpdateOrderStatusDto;
import com.bookshop.model.User;
import com.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order management", description = "Endpoints for order books")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place order.",
            description = "Place new order with books in shopping cart.")
    public OrderDto placeOrder(@RequestBody @Valid PlaceOrderDto orderDto,
                               @AuthenticationPrincipal User user) {
        return orderService.placeOrder(user.getId(), orderDto);
    }

    @GetMapping
    @Operation(summary = "Get all orders.",
            description = "Get all orders of user.")
    public Page<OrderDto> getAllOrders(@AuthenticationPrincipal User user,
                                       @ParameterObject Pageable pageable) {
        return orderService.getAllOrders(user.getId(), pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change order status.",
            description = "Change order status by its id.")
    public OrderDto changeOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusDto updateOrderStatus
    ) {
        return orderService.updateOrderStatus(updateOrderStatus, id);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all items for order",
            description = "Get all items for a order.")
    public Page<OrderItemDto> getAllOrderItems(@PathVariable Long orderId,
                                               @ParameterObject Pageable pageable) {
        return orderService.getOrderItems(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get item for order",
            description = "Get item for order by its id")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
