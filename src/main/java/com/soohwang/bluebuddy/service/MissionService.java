package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.NewCreatureDto;
import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final SeaCreatureRepository seaCreatureRepository;
    private final UserCreatureService userCreatureService;

    public SeaCreature completeMission(String email, Long spotId) {
        List<SeaCreature> allCreatures = seaCreatureRepository.findBySpot_SpotId(spotId);
        if (allCreatures.isEmpty())  {
            throw new RuntimeException("해당 서식지에 생물이 없습니다.");
        }

        SeaCreature selected = userCreatureService.getRandomCreature(allCreatures);
        userCreatureService.addCreatureToUser(email, selected);

        return selected;
    }
}
