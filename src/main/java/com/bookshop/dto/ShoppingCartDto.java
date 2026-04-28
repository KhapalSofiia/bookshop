package com.bookshop.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private long id;
    private long userId;
    private List<Long> cartItemIds;
}
