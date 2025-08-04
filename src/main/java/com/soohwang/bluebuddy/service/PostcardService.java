package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.UserPostcardDto;
import com.soohwang.bluebuddy.entity.*;
import com.soohwang.bluebuddy.repository.PostcardImageRepository;
import com.soohwang.bluebuddy.repository.PostcardTextRepository;
import com.soohwang.bluebuddy.repository.UserPostcardRepository;
import com.soohwang.bluebuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PostcardService {

    private final UserRepository userRepository;
    private final PostcardImageRepository postcardImageRepository;
    private final PostcardTextRepository postcardTextRepository;
    private final UserPostcardRepository userPostcardRepository;

    private final Random random = new Random();

    @Transactional
    public void addRandomPostcard(User user) {
        List<PostcardImage> images = postcardImageRepository.findAll();
        List<PostcardText> texts = postcardTextRepository.findAll();

        if (images.isEmpty() || texts.isEmpty()) {
            throw new RuntimeException("엽서 이미지 또는 텍스트가 존재하지 않습니다.");
        }

        PostcardImage image = images.get(random.nextInt(images.size()));
        PostcardText text = texts.get(random.nextInt(texts.size()));

        UserPostcard up = new UserPostcard();
        up.setUser(user);
        up.setPostcardImage(image);
        up.setPostcardText(text);

        userPostcardRepository.save(up);
    }

    @Transactional
    public List<UserPostcardDto> getPostcardsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<UserPostcard> creatures = userPostcardRepository.findPostcardsByUser(user);

        return creatures.stream()
                .map(this::toResponse)
                .toList();
    }
    private UserPostcardDto toResponse(UserPostcard up) {
        return UserPostcardDto.builder()
                .userPostcardId(up.getUserPostcardId())
                .postcardImagePath(up.getPostcardImage().getImagePath())
                .postcardText(up.getPostcardText().getPostcardText())
                .build();
    }
}
