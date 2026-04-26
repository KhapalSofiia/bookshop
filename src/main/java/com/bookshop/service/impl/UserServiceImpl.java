package com.bookshop.service.impl;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.exception.RegistrationException;
import com.bookshop.mapper.UserMapper;
import com.bookshop.model.Role;
import com.bookshop.model.User;
import com.bookshop.model.enums.RoleName;
import com.bookshop.repository.RoleRepository;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.ShoppingCartService;
import com.bookshop.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new RegistrationException("User with email "
                    + userRegistrationDto.getEmail()
                    + " already exists.");
        }
        User user = userMapper.toModel(userRegistrationDto);

        user.setPassword(encoder.encode(userRegistrationDto.getPassword()));

        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException("Role USER not found."));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);

        shoppingCartService.createShoppingCart(savedUser);

        return userMapper.toUserDto(savedUser);
    }
}
