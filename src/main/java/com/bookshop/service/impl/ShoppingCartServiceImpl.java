package com.bookshop.service.impl;

import com.bookshop.dto.AddBookToCartDto;
import com.bookshop.dto.ShoppingCartDto;
import com.bookshop.dto.UpdateCartItemDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.ShoppingCartMapper;
import com.bookshop.model.Book;
import com.bookshop.model.CartItem;
import com.bookshop.model.ShoppingCart;
import com.bookshop.model.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.ShoppingCartRepository;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Transactional
    @Override
    public ShoppingCartDto addBookToCart(String email, AddBookToCartDto addBookToCartDto) {
        Book book = bookRepository.findById(addBookToCartDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + addBookToCartDto.getBookId() + " not found"));
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);

        Optional<CartItem> existingItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity()
                    + addBookToCartDto.getQuantity());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(addBookToCartDto.getQuantity());
            cartItem.setBook(book);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        }

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public ShoppingCartDto updateBookQuantity(String email, Long cartItemId,
                                              UpdateCartItemDto updateCartItemDto) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);
        CartItem cartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found in cart"));

        cartItem.setQuantity(updateCartItemDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public void removeBookFromCart(String email, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);
        CartItem cartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no such item in shopping cart"));

        shoppingCart.getCartItems().remove(cartItem);
        cartItem.setShoppingCart(null);
    }

    @Override
    @Transactional
    public ShoppingCartDto getCart(String email) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart getShoppingCartByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User with email "
                        + email + "not found"));
        return shoppingCartRepository.findByUser(user).orElseThrow(() ->
                new EntityNotFoundException("ShoppingCart not found"));
    }
}
