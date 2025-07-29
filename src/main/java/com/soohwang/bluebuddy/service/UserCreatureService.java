package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserCreature;
import com.soohwang.bluebuddy.repository.UserCreatureRepository;
import com.soohwang.bluebuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreatureService {

    private final UserRepository userRepository;
    private final UserCreatureRepository userCreatureRepository;

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
}
