package com.soohwang.bluebuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotNull
    private String email;
    private String code;
}
