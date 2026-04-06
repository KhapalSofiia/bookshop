package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import com.bookshop.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationDto toUserRegistrationRequestDto(User user);

    UserDto toUserDto(User user);

    User toModel(UserDto userDto);

    User toModel(UserRegistrationDto userRegistrationDto);
}
