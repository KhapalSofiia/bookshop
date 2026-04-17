package com.bookshop.dto;

import lombok.Data;

@Data
public class AddBookToCartDto {
    private Long bookId;
    private int quantity;
}
