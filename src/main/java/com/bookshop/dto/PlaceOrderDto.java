package com.bookshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaceOrderDto {
    @NotBlank
    private String shippingAddress;
}
