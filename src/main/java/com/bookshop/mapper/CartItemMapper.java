package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.CartItemDto;
import com.bookshop.model.Book;
import com.bookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId")
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toModel(CartItemDto cartItemDto);

    default Book mapBook(Long id) {
        return id == null ? null : new Book(id);
    }
}
