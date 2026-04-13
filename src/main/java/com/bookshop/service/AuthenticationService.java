package com.bookshop.service;

import com.bookshop.dto.UserLoginRequestDto;
import com.bookshop.dto.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);
}
