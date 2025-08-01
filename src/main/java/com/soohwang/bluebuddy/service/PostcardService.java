package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.entity.PostcardImage;
import com.soohwang.bluebuddy.entity.PostcardText;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserPostcard;
import com.soohwang.bluebuddy.repository.PostcardImageRepository;
import com.soohwang.bluebuddy.repository.PostcardTextRepository;
import com.soohwang.bluebuddy.repository.UserPostcardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PostcardService {

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
}
