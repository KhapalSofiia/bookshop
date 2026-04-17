package com.bookshop.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ShoppingCartDto {
    private long id;
    @NotNull
    private long userId;
    private List<Long> cartItemIds;
}
