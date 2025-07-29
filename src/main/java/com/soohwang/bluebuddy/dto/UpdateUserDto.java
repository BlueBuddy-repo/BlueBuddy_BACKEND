package com.soohwang.bluebuddy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;
}
