package com.bookshop.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddBookToCartDto {
    private Long bookId;
    @Positive
    private int quantity;
}
