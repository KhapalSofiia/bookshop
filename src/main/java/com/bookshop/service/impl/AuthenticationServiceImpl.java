package com.bookshop.service.impl;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import com.bookshop.exception.RegistrationException;
import com.bookshop.mapper.UserMapper;
import com.bookshop.model.User;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByEmail(userRegistrationDto.getEmail())) {
            throw new RegistrationException("User with this email already exists.");
        }
        if (userRegistrationDto.getPassword()
                .equals(userRegistrationDto.getRepeatPassword())) {
            throw new RegistrationException("Passwords don't match.");
        }
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }
}
