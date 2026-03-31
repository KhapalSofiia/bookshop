package com.bookshop.controller;

import com.bookshop.dto.UserDto;
import com.bookshop.dto.UserRegistrationDto;
import com.bookshop.exception.RegistrationException;
import com.bookshop.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth management", description = "Endpoints for ")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @RequestMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Creates a new user")
    public UserDto registration(@RequestBody @Valid UserRegistrationDto userRegistrationDto)
            throws RegistrationException{
        return authenticationService.registration(userRegistrationDto);
    }
}
