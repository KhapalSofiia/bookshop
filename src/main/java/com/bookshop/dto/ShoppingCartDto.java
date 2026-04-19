package com.bookshop.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
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
