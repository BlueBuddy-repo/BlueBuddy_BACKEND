package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.ChangePetDto;
import com.soohwang.bluebuddy.dto.PetDto;
import com.soohwang.bluebuddy.entity.Pet;
import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.repository.PetRepository;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final SeaCreatureRepository seaCreatureRepository;
    private final UserService userService;

    // 나의 펫 불러오기
    public PetDto getMyPet(User user) {
        Pet pet = petRepository.getPetByUser(user)
                .orElseThrow(()-> new IllegalStateException("펫이 없습니다."));

        return PetDto.builder()
                .PetName(pet.getPetName())
                .PetImage("펫 이미지 경로")
                .creatureId(pet.getSeaCreature().getCreatureId())
                .build();
    }
}
