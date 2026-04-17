package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.CartItemDto;
import com.bookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "shoppingCart.id", source = "shoppingCartId")
    @Mapping(target = "book.id", source = "bookId")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "shoppingCartId", source = "shoppingCart")
    @Mapping(target = "bookId", source = "book")
    CartItem toModel(CartItemDto cartItemDto);
}
