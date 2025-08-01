package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.UserCreatureDto;
import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserCreature;
import com.soohwang.bluebuddy.repository.UserCreatureRepository;
import com.soohwang.bluebuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreatureService {

    private final UserRepository userRepository;
    private final UserCreatureRepository userCreatureRepository;

    @Transactional
    public void addCreatureToUser(String email, SeaCreature creature) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        boolean exists = userCreatureRepository.existsByUserAndSeaCreature(user, creature);
        if (!exists) {
            UserCreature uc = new UserCreature();
            uc.setUser(user);
            uc.setSeaCreature(creature);
            userCreatureRepository.save(uc);
        }
    }

    @Transactional
    public List<UserCreatureDto> getCreaturesByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<SeaCreature> creatures = userCreatureRepository.findSeaCreaturesByUser(user);

        return creatures.stream()
                .map(this::toResponse)
                .toList();
    }
    private UserCreatureDto toResponse(SeaCreature sc) {
        return UserCreatureDto.builder()
                .creatureId(sc.getCreatureId())
                .nameKr(sc.getNameKr())
                .nameEn(sc.getNameEn())
                .scientificName(sc.getScientificName())
                .habitat(sc.getHabitat())
                .endangermentLevel(sc.getEndangermentLevel())
                .description(sc.getDescription())
                .build();
    }
}
