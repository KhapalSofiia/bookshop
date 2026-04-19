package com.bookshop.service;

import com.bookshop.dto.AddBookToCartDto;
import com.bookshop.dto.ShoppingCartDto;
import com.bookshop.dto.UpdateCartItemDto;

public interface ShoppingCartService {
    ShoppingCartDto addBookToCart(String email, AddBookToCartDto addBookToCartDto);

    ShoppingCartDto updateBookQuantity(String email, Long bookId,
                                       UpdateCartItemDto updateCartItemDto);

    void removeBookFromCart(String email, Long cartItemId);

    ShoppingCartDto getCart(String email);

}
