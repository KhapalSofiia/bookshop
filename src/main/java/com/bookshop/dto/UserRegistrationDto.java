package com.bookshop.dto;

import com.bookshop.annotations.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(
        message = "Password and repeated password must be equal"
)public class UserRegistrationDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 6, max = 35)
    private String password;
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
