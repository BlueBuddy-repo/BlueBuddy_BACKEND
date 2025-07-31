package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.SignupDto;
import com.soohwang.bluebuddy.dto.UpdateUserDto;
import com.soohwang.bluebuddy.entity.Pet;
import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserCreature;
import com.soohwang.bluebuddy.jwt.JwtUtil;
import com.soohwang.bluebuddy.repository.PetRepository;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import com.soohwang.bluebuddy.repository.UserCreatureRepository;
import com.soohwang.bluebuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SeaCreatureRepository seaCreatureRepository;
    private final PetRepository petRepository;
    private final UserCreatureRepository userCreatureRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
        SeaCreature seaCreature = seaCreatureRepository.findRandomOne(); // 나중에 희귀 정도가 낮은 것만 배정하도록 추가
        if (seaCreature == null) {
            throw new IllegalStateException("해양 생물 배정 실패");
        }

        Pet pet = Pet.builder()
                .petName("블루")
                .seaCreature(seaCreature)
                .user(user)
                .build();
        petRepository.save(pet);
        userCreatureRepository.save(new UserCreature(user, seaCreature)); // 획득한 펫을 생물도감에 등록

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
        user.setPassword(passwordEncoder.encode(updateUserDto.getNewPassword()));
    }
}
