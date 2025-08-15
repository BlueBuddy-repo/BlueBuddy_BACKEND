package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.UserSpotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@AllArgsConstructor
public class UserSpotController {
    private final UserSpotService userSpotService;

    @PostMapping(value = "/zeroWaste",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> isZeroWaste(@AuthenticationPrincipal User user,
                                                   @RequestPart("file") MultipartFile image) {
        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "사용자 없음", null));
            }
            boolean isZeroWaste = userSpotService.isZeroWaste(user, image);
            return ResponseEntity.ok(new ApiResponse(true, "제로웨이스트 검증 성공", isZeroWaste));
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "AI 호출 실패", null));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @GetMapping("/getMissionNum")
    public ResponseEntity<ApiResponse> isZeroWaste(@RequestParam(value = "spotId") Long spotId) {
        try {
            int result = userSpotService.getMissionNum(spotId);
            return ResponseEntity.ok(new ApiResponse(true, "해당 스팟의 미션 수 반환 성공", result));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
