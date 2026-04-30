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
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public ShoppingCartDto addBookToCart(Long userId, AddBookToCartDto addBookToCartDto) {
        Book book = bookRepository.findById(addBookToCartDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + addBookToCartDto.getBookId() + " not found"));
        ShoppingCart shoppingCart = getCartOrThrow(userId);

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
    public ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                              UpdateCartItemDto updateCartItemDto) {
        ShoppingCart shoppingCart = getCartOrThrow(userId);

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found in cart"));

        cartItem.setQuantity(updateCartItemDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public void removeBookFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getCartOrThrow(userId);

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id" + cartItemId + " not found in cart"));

        shoppingCart.getCartItems().remove(cartItem);
    }

    @Override
    @Transactional
    public ShoppingCartDto getCart(Long userId) {
        ShoppingCart shoppingCart = getCartOrThrow(userId);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        shoppingCartRepository.save(cart);
    }

    protected ShoppingCart getCartOrThrow(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart for user " + userId + " not found"));
    }
}
