package com.bookshop.validators;

import com.bookshop.annotations.FieldMatch;
import com.bookshop.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator
        implements ConstraintValidator<FieldMatch, UserRegistrationDto> {

    @Override
    public boolean isValid(
            UserRegistrationDto dto,
            ConstraintValidatorContext context
    ) {
        return dto.getPassword() != null && dto.getPassword()
                .equals(dto.getRepeatPassword());
    }
}
