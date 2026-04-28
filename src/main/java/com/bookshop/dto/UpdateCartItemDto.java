package com.bookshop.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @Positive
    private int quantity;
}
