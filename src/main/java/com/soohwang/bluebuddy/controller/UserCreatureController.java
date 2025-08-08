package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.CreatureDetailDto;
import com.soohwang.bluebuddy.dto.CreatureThumbnailDto;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.UserCreatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-creatures")

public class UserCreatureController {
    private final UserCreatureService userCreatureService;

    @GetMapping("/my")
    public ResponseEntity<ApiResponse> getUserCreatures(@AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "인증되지 않은 사용자입니다.", null));
            }

            String email = user.getEmail();
            List<CreatureThumbnailDto> result = userCreatureService.getCreatureThumbnails(email);

            return ResponseEntity.ok(new ApiResponse(true, "보유 생물 조회 성공", result));
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

    @GetMapping("/{creatureId}")
    public ResponseEntity<ApiResponse> getCreatureDetail(@PathVariable Long creatureId,
                                                         @AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "인증되지 않은 사용자입니다.", null));
            }

            CreatureDetailDto result = userCreatureService.getCreatureDetail(creatureId);

            return ResponseEntity.ok(new ApiResponse(true, "생물 상세 조회 성공", result));
        } catch (RuntimeException ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("해당 생물 없음")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
