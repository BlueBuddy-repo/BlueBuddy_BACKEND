package com.soohwang.bluebuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
