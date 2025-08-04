package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.UserPostcardDto;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.PostcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-postcards")
public class PostcardController {
    private final PostcardService postcardService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserPostcards(@AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "인증되지 않은 사용자입니다.", null));
            }

            String email = user.getEmail();
            List<UserPostcardDto> result = postcardService.getPostcardsByUserEmail(email);

            return ResponseEntity.ok(new ApiResponse(true, "보유 엽서 조회 성공", result));
        }catch (RuntimeException ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("사용자 없음")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, ex.getMessage(), null));
            }
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
