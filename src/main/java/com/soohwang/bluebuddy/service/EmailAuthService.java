package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public void sendCodeToEmail(String email) {
        String code = generateCode();
        saveAuthCodeToRedis(email, code);
        sendEmail(email, code);
    }

    private String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6자리 숫자
    }

    private void saveAuthCodeToRedis(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 3, TimeUnit.MINUTES); // TTL 3분
    }

    private void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[BlueBuddy] 회원가입 인증 코드");
        message.setText("인증 코드는 다음과 같습니다: " + code);
        mailSender.send(message);
    }

    public void verifyCode(EmailDto emailDto) {
        String redisCode = redisTemplate.opsForValue().get(emailDto.getEmail());
        if (redisCode == null) {
            throw new IllegalArgumentException("인증번호가 만료되었거나 존재하지 않습니다.");
        }
        if (!redisCode.equals(emailDto.getCode())) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
        redisTemplate.delete(emailDto.getCode()); // 인증 후 삭제
        String verifiedKey = "verified:" + emailDto.getEmail();
        redisTemplate.opsForValue().set(verifiedKey, "true", 5, TimeUnit.MINUTES); // 인증 완료 플래그 저장
    }

    public boolean isEmailVerified(String email) {
        String key = "verified:" + email;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
