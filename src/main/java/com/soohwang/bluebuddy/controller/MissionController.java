package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.NewCreatureDto;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/complete")
    public ResponseEntity<ApiResponse> attendance(@AuthenticationPrincipal User user,  @RequestParam String habitat) {
        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "인증되지 않은 사용자입니다.", null));
            }

            String email = user.getEmail();
            NewCreatureDto creature = missionService.completeMission(email, habitat);

            missionService.completeMission(email, habitat);

                return ResponseEntity.ok(new ApiResponse(true, "생물 획득 완료", creature));
            } catch (RuntimeException ex) {
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

