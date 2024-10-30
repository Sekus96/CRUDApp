package com.example.CRUDApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Username is required.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "Login must contain at least 4 characters and not more than 20 characters."
    )
    @Schema(example = "enter_your_login")
    private String username;
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must contain at least 8 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!.]).+$",
            message = "Password must contain at least one uppercase letter, one digit, and one special character."
    )
    @Schema(example = "enter_your_password")
    private String password;
}
