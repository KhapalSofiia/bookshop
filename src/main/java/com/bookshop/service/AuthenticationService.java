package com.bookshop.service;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    UserDto registration(UserRegistrationDto userRegistrationDto);
}
