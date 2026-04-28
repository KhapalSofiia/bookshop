package com.bookshop.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long id;
    private long bookId;
    private String bookTitle;
    private int quantity;
}
