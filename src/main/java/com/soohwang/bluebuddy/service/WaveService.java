package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaveService {
    private final UserRepository userRepository;

    @Transactional
    public void attendance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        Long waveIndex = user.getWaveIndex();
        user.setWaveIndex(waveIndex + 5);
    }
}
