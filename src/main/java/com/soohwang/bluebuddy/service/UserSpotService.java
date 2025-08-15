package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.SpotDataDto;
import com.soohwang.bluebuddy.entity.Spot;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserSpot;
import com.soohwang.bluebuddy.repository.SeaCreatureRepository;
import com.soohwang.bluebuddy.repository.SpotRepository;
import com.soohwang.bluebuddy.repository.UserSpotRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@Service
@AllArgsConstructor
public class UserSpotService {
    private final WebClient webClient = WebClient.create("http://localhost:8000"); // FastAPI 주소
    private final UserSpotRepository userSpotRepository;
    private final SeaCreatureRepository seaCreatureRepository;
    private final UserCreatureService userCreatureService;
    private final SpotRepository spotRepository;
    private final MissionService missionService;

    @Transactional
    public Boolean isZeroWaste(User user, MultipartFile image) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", image.getResource());
            // 1. 이미지 → FastAPI 호출
            String result = webClient.post()
                    .uri("/predict")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // 동기 처리

            if (!"reusable".equalsIgnoreCase(Objects.requireNonNull(result).trim())) {
                System.out.println("제로웨이스트아님");
                return false;
            }

            // 2. 사용자 가장 높은 spotId 조회
            UserSpot currentUserSpot = userSpotRepository.findTopByUser_UserIdOrderBySpot_SpotIdDesc(user.getUserId())
                    .orElseThrow(() -> new IllegalStateException("사용자의 장소 정보가 없습니다."));

            // 3. 미션 개수 +1
            currentUserSpot.increaseCompletedMission();
            userSpotRepository.save(currentUserSpot);

            // 4. 미션 완료 여부 확인
            if (currentUserSpot.getMissionCount() >= currentUserSpot.getSpot().getMissionNum()) {
                // 5-1. 스팟 완료 처리
                currentUserSpot.setIsCompleted(true);
                // 5-2. 새로운 생물 지급
                // spot의 장소명을 사용하여 해당하는 생물 중 하나를 랜덤 배정? 논의 필요
                missionService.completeMission(user.getEmail(), currentUserSpot.getSpot().getSpotId());

                // 6. 다음 장소 할당 (spotId + 1)
                Long nextSpotId = currentUserSpot.getSpot().getSpotId() + 1;
                Spot nextSpot = spotRepository.findById(nextSpotId)
                        .orElseThrow(() -> new IllegalStateException("다음 장소가 존재하지 않습니다."));

                UserSpot newUserSpot = UserSpot.builder()
                        .user(user)
                        .spot(nextSpot)
                        .missionCount(0)
                        .isCompleted(false)
                        .build();
                userSpotRepository.save(newUserSpot);
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getMissionNum(Long spotId) {
        Integer missionNum = spotRepository.getMissionNumBySpotId(spotId);
        if (missionNum == null) {
            throw new NoSuchElementException("해당 spotId의 미션 개수를 찾을 수 없습니다.");
        }
        return missionNum;
    }

    public List<SpotDataDto> getSpotDataDto(User user) {
        List<Spot> allSpots = spotRepository.findAll();
        List<SpotDataDto> spotDataDtos = new ArrayList<>();

        for (Spot spot : allSpots) {
            UserSpot userSpot = userSpotRepository.findByUserAndSpot(user, spot).orElse(null);

            SpotDataDto dto = new SpotDataDto();
            dto.setSpotId(spot.getSpotId().toString());
            dto.setSpotName(spot.getSpotName());
            dto.setLatitude(spot.getLatitude());
            dto.setLongitude(spot.getLongitude());

            if (userSpot == null) {
                // 스팟을 연 적이 없음
                dto.setIsCompleted(null);
                dto.setMissionCount(0);
            } else {
                dto.setIsCompleted(userSpot.getIsCompleted());
                dto.setMissionCount(userSpot.getMissionCount());
            }

            spotDataDtos.add(dto);
        }
        return spotDataDtos;
    }
}
