package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.CreatureByHabitatDto;
import com.soohwang.bluebuddy.service.SeaCreatureService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sea-creatures")
@RequiredArgsConstructor
public class SeaCreatureController {
    private final SeaCreatureService seaCreatureService;

    @GetMapping("/habitat")
    public List<CreatureByHabitatDto> getByHabitat(@RequestParam String habitat) {
        return seaCreatureService.getCreaturesByHabitat(habitat);
    }
}
