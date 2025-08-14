package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.CreatureByHabitatDto;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeaCreatureService {
    private final SeaCreatureRepository seaCreatureRepository;

    public List<CreatureByHabitatDto> getCreaturesByHabitat(Long spotId) {
        return seaCreatureRepository.findCreatureDtoBySpotId(spotId);
    }

}
