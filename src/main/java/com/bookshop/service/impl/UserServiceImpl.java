package com.bookshop.service.impl;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import com.bookshop.exception.RegistrationException;
import com.bookshop.mapper.UserMapper;
import com.bookshop.model.User;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new RegistrationException("User with email "
                    + userRegistrationDto.getEmail()
                    + " already exists.");
        }

        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setShippingAddress(userRegistrationDto.getShippingAddress());
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }
}
