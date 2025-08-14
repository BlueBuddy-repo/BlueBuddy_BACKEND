package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.dto.SpeciesDto;
import com.soohwang.bluebuddy.entity.Species;
import com.soohwang.bluebuddy.repository.SpeciesRepository;
import com.soohwang.bluebuddy.scheduler.SpeciesScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesRepository speciesRepository;
    private final SpeciesScheduler speciesScheduler;

    @GetMapping("/update")
    public String updateSpeciesNow() {
        speciesScheduler.updateSpecies();
        return "OBIS 해양 생물 종 수 저장 완료 (수동 실행)";
    }

    @GetMapping
    public List<SpeciesDto> getAllSpecies() {
        return speciesRepository.findAll().stream()
                .map(species -> SpeciesDto.builder()
                        .id(species.getId())
                        .latMin(species.getLatMin())
                        .latMax(species.getLatMax())
                        .lonMin(species.getLonMin())
                        .lonMax(species.getLonMax())
                        .speciesCount(species.getSpeciesCount())
                        .updatedAt(species.getUpdatedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
