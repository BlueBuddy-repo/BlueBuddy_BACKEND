package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.EmailDto;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.EmailAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @PostMapping("/sendCode")
    public ResponseEntity<ApiResponse> sendCode(@RequestParam String email) {
        try {
            emailAuthService.sendCodeToEmail(email);
            return ResponseEntity.ok(new ApiResponse(true, "인증 코드가 전송되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<ApiResponse> verifyCode(@RequestBody @Valid EmailDto emailDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = Optional.ofNullable(bindingResult.getFieldError())
                        .map(FieldError::getDefaultMessage)
                        .orElse("유효성 검사 실패");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, errorMessage, null));
            }
            emailAuthService.verifyCode(emailDto);
            return ResponseEntity.ok(new ApiResponse(true, "인증 성공", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "인증 실패: " + ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}