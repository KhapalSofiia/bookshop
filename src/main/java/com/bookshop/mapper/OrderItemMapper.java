package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.OrderItemDto;
import com.bookshop.model.CartItem;
import com.bookshop.model.Order;
import com.bookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toModel(OrderItemDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", source = "order")
    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "quantity", source = "cartItem.quantity")
    @Mapping(target = "price", source = "cartItem.book.price")
    OrderItem cartItemToOrderItem(CartItem cartItem, Order order);
}
