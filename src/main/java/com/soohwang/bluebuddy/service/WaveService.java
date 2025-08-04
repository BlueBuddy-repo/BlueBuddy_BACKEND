package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaveService {
    private final UserRepository userRepository;
    private final PostcardService postcardService;

    @Transactional
    public void attendance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        Long waveIndex = user.getWaveIndex() + 5;
        user.setWaveIndex(waveIndex);

        if (waveIndex % 50 == 0) {
            postcardService.addRandomPostcard(user);
        }
    }
}
