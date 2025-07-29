package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.WaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wave")
@RequiredArgsConstructor
public class WaveController {
    private final WaveService waveService;

    @PutMapping("/attendance")
    public ResponseEntity<ApiResponse> attendance(@AuthenticationPrincipal User user) {
        try {
            waveService.attendance(user.getEmail());
            return ResponseEntity.ok(new ApiResponse(true, "출석체크 완료", null));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
}
