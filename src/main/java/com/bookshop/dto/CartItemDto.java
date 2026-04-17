package com.bookshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CartItemDto {
    private long id;
    @NotNull
    private long bookId;
    @NotNull
    private String bookTitle;
    @Positive
    private int quantity;
}
