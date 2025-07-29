package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final SeaCreatureRepository seaCreatureRepository;
    private final UserCreatureService userCreatureService;

    public void completeMission(String email, String habitat) {
        List<SeaCreature> allCreatures = seaCreatureRepository.findByHabitat(habitat);
        if (allCreatures.isEmpty()) return;

        SeaCreature selected = getRandomCreature(allCreatures);
        userCreatureService.addCreatureToUser(email, selected);
    }

    private SeaCreature getRandomCreature(List<SeaCreature> creatures) {
        Random random = new Random();
        int index = random.nextInt(creatures.size());
        return creatures.get(index);
    }
}
