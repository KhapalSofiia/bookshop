package com.bookshop.service;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;

public interface UserService {
    UserDto registration(UserRegistrationDto userRegistrationDto);
}
