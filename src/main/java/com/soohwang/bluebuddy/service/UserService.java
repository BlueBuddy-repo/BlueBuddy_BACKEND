package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.SignupDto;
import com.soohwang.bluebuddy.dto.UpdateUserDto;
import com.soohwang.bluebuddy.entity.*;
import com.soohwang.bluebuddy.jwt.JwtUtil;
import com.soohwang.bluebuddy.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreatureRepository userCreatureRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserCreatureService userCreatureService;
    private final SeaCreatureRepository seaCreatureRepository;
    private final UserSpotRepository userSpotRepository;
    private final SpotRepository spotRepository;

    @Transactional
    public void signup(SignupDto signupDto) {
        if (userRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 사용자 정보 저장
        User user = User.builder()
                .email(signupDto.getEmail())
                .name(signupDto.getName())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .waveIndex(0L)
                .build();
        userRepository.save(user);

        // 펫 획득
        List<SeaCreature> allSeaCreature = seaCreatureRepository.findAll();
        SeaCreature seaCreature = userCreatureService.getRandomCreature(allSeaCreature); // 랜덤 펫
        if (seaCreature == null) {
            throw new IllegalStateException("해양 생물 배정 실패");
        }

        UserCreature userCreature = UserCreature.builder()
                .user(user)
                .seaCreature(seaCreature)
                .petName("블루") // 기본 이름
                .selected(true)
                .build();
        userCreatureRepository.save(userCreature); // 획득한 펫을 생물도감에 등록

        // 첫번째 스팟 오픈
        Spot firstSpot = spotRepository.getBySpotId(1L);
        UserSpot newUserSpot = UserSpot.builder()
                .user(user)
                .spot(firstSpot)
                .missionCount(0)
                .isCompleted(false) // 미션 진행중
                .isOpened(false) // 사용자 클릭 x
                .seaCreature(null) // 배정된 생물 없음
                .build();
        userSpotRepository.save(newUserSpot);

    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(user.getEmail());
    }

    @Transactional
    public void updateMyInfo(UpdateUserDto updateUserDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(updateUserDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 틀렸습니다.");
        }

        user.setName(updateUserDto.getName());

        if (updateUserDto.getNewPassword() != null && !updateUserDto.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getNewPassword()));
        }
    }
}
