package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.LoginDto;
import com.soohwang.bluebuddy.dto.SignupDto;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.jwt.JwtUtil;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupDto signupDto) {
        if (userRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(signupDto.getEmail())
                .name(signupDto.getName())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();

        userRepository.save(user);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(user.getEmail());
    }
}
