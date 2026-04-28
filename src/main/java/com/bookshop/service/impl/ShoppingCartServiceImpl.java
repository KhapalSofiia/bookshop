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
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.ShoppingCartRepository;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public ShoppingCartDto addBookToCart(String email, AddBookToCartDto addBookToCartDto) {
        Book book = bookRepository.findById(addBookToCartDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + addBookToCartDto.getBookId() + " not found"));
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(addBookToCartDto.getQuantity());
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public ShoppingCartDto updateBookQuantity(String email, Long cartItemId,
                                              UpdateCartItemDto updateCartItemDto) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found in cart"));

        cartItem.setQuantity(updateCartItemDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public void removeBookFromCart(String email, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found in cart"));

        shoppingCart.getCartItems().remove(cartItem);
        cartItem.setShoppingCart(null);
    }

    @Override
    @Transactional
    public ShoppingCartDto getCart(String email) {
        ShoppingCart shoppingCart = getShoppingCartByEmail(email);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    protected ShoppingCart getShoppingCartByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User with email "
                        + email + "not found"));
        return shoppingCartRepository.findByUser(user).orElseThrow(() ->
                new EntityNotFoundException("ShoppingCart of user with email: "
                        + user.getEmail() + " not found"));
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        shoppingCartRepository.save(cart);
    }
}
