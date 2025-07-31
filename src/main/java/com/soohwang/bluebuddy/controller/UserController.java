package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.SignupDto;
import com.soohwang.bluebuddy.dto.UpdateUserDto;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/signup")
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
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 로그인
    @PostMapping("/auth/login")
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

    @GetMapping("/user/my")
    public ResponseEntity<ApiResponse> getMyInfo(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "사용자 없음", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "나의 정보 불러오기 성공", user.getName()));
    }

    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse> updateMyInfo (@RequestBody UpdateUserDto updateUserDto, @AuthenticationPrincipal User user) {
        try {
            userService.updateMyInfo(updateUserDto, user.getEmail());
            return ResponseEntity.ok(new ApiResponse(true, "나의 정보 수정 성공", null));
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