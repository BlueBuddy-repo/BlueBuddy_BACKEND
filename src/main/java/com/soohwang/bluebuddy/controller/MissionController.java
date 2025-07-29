package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> completeMission(@RequestParam String email, @RequestParam String habitat) {
        missionService.completeMission(email, habitat);
        return ResponseEntity.ok("생물 획득 완료");
    }
}

