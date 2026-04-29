package com.bookshop.service;

import com.bookshop.dto.AddBookToCartDto;
import com.bookshop.dto.ShoppingCartDto;
import com.bookshop.dto.UpdateCartItemDto;
import com.bookshop.model.User;

public interface ShoppingCartService {
    ShoppingCartDto addBookToCart(Long userId, AddBookToCartDto addBookToCartDto);

    ShoppingCartDto updateBookQuantity(Long userId, Long bookId,
                                       UpdateCartItemDto updateCartItemDto);

    void removeBookFromCart(Long userId, Long cartItemId);

    ShoppingCartDto getCart(Long userId);

    void createShoppingCart(User user);
}
