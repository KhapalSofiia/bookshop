package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.ShoppingCartDto;
import com.bookshop.model.CartItem;
import com.bookshop.model.ShoppingCart;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bookshop.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItemIds", ignore = true)
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setCartItemIds(@MappingTarget ShoppingCartDto shoppingCartDto,
                                ShoppingCart shoppingCart) {
        List<Long> cartItemIds = shoppingCart.getCartItems()
                .stream()
                .map(CartItem::getId)
                .toList();

        shoppingCartDto.setCartItemIds(cartItemIds);
    }

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "cartItems", ignore = true)
    ShoppingCart toModel(ShoppingCartDto dto);

    @AfterMapping
    default void setCartItems(@MappingTarget ShoppingCart shoppingCart,
                              ShoppingCartDto shoppingCartDto) {
        Set<CartItem> cartItems = shoppingCartDto.getCartItemIds()
                .stream()
                .map(CartItem::new)
                .collect(Collectors.toSet());

        shoppingCart.setCartItems(cartItems);
    }

    default User mapUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
