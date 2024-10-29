package com.example.CRUDApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDto {
    @Schema(example = "enter_your_login")
    private String username;
    @Schema(example = "enter_your_password")
    private String password;
}
