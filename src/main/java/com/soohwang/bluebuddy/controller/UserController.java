package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.SignupDto;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.jwt.JwtUtil;
import com.soohwang.bluebuddy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid SignupDto requestDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = Optional.ofNullable(bindingResult.getFieldError())
                        .map(FieldError::getDefaultMessage)
                        .orElse("유효성 검사 실패");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, errorMessage, null));
            }
            userService.signup(requestDto);
            return ResponseEntity.ok(new ApiResponse(true, "회원가입 성공", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        try {
            String token = userService.login(loginDto);
            return ResponseEntity.ok(new ApiResponse(true, "로그인 성공", token));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}