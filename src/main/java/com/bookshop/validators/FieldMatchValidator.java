package com.bookshop.validators;

import com.bookshop.annotations.FieldMatch;
import com.bookshop.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator
        implements ConstraintValidator<FieldMatch, UserRegistrationDto> {

    @Override
    public boolean isValid(
            UserRegistrationDto dto,
            ConstraintValidatorContext context
    ) {
        return Objects.equals(dto.getPassword(), dto.getRepeatPassword());
    }
}
