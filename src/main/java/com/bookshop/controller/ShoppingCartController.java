package com.bookshop.controller;

import com.bookshop.dto.AddBookToCartDto;
import com.bookshop.dto.ShoppingCartDto;
import com.bookshop.dto.UpdateCartItemDto;
import com.bookshop.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @Operation(summary = "Add book to shopping cart",
            description = "Add book to shopping cart")
    public ShoppingCartDto addBookToCart(@RequestBody @Valid AddBookToCartDto addBookToCartDto) {
        return shoppingCartService.addBookToCart(getEmail(), addBookToCartDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Change quantity of books in shopping cart",
            description = "Change quantity of books in shopping cart")
    public ShoppingCartDto updateBookQuantity(@PathVariable Long cartItemId,
                                              @RequestBody @Valid UpdateCartItemDto dto) {
        return shoppingCartService.updateBookQuantity(
                getEmail(),
                cartItemId,
                dto
        );
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book from shopping cart",
            description = "Delete book from shopping cart")
    public void removeBookFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.removeBookFromCart(getEmail(), cartItemId);
    }

    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Get in shopping cart")
    public ShoppingCartDto getCart() {
        return shoppingCartService.getCart(getEmail());
    }

    protected static String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
