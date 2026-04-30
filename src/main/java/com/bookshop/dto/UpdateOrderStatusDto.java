package com.bookshop.dto;

import com.bookshop.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {
    @NotNull
    private OrderStatus status;
}
