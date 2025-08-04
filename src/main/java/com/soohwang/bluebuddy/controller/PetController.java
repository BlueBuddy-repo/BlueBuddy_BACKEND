package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.ChangePetDto;
import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.PetDto;
import com.soohwang.bluebuddy.entity.Pet;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/myPet")
    public ResponseEntity<ApiResponse> getMyPet(@AuthenticationPrincipal User user) {
        try {
            PetDto pet = petService.getMyPet(user);
            return ResponseEntity.ok(new ApiResponse(true, "펫 불러오기 성공", pet));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PutMapping("myPet/change")
    public ResponseEntity<ApiResponse> changeMyPet(@AuthenticationPrincipal User user, @RequestBody ChangePetDto changePetDto) {
        try {
            petService.changeMyPet(user, changePetDto);
            return ResponseEntity.ok(new ApiResponse(true, "펫 정보 수정 성공", null));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

}
