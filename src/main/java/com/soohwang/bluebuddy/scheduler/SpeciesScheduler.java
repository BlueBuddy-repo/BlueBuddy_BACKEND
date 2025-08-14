package com.soohwang.bluebuddy.scheduler;

import com.soohwang.bluebuddy.entity.Species;
import com.soohwang.bluebuddy.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class SpeciesScheduler {

    private final SpeciesRepository speciesRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    //@Scheduled(cron = "0 0 0 1 * *")
    public void updateSpecies() {
        List<Species> speciesList = new ArrayList<>();
        int totalCells = 36 * 72;
        int count = 0;

        for (int lat = -90; lat < 90; lat += 5) {
            for (int lon = -180; lon < 180; lon += 5) {

                String polygon = String.format(
                        "POLYGON((%d %d,%d %d,%d %d,%d %d,%d %d))",
                        lon, lat,
                        lon, lat + 5,
                        lon + 5, lat + 5,
                        lon + 5, lat,
                        lon, lat
                );

                try {
                    String url = "https://api.obis.org/v3/occurrence?geometry=" + polygon;
                    String response = restTemplate.getForObject(url, String.class);
                    JSONObject json = new JSONObject(response);
                    JSONArray results = json.getJSONArray("results");

                    // marine == true && scientificName 바다면서 존재하는 종만 카운트
                    Set<String> uniqueMarineSpecies = new HashSet<>();
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject obj = results.getJSONObject(i);
                        if (obj.optBoolean("marine", false) && obj.has("scientificName")) {
                            uniqueMarineSpecies.add(obj.getString("scientificName"));
                        }
                    }

                    int speciesCount = uniqueMarineSpecies.size();

                    if (speciesCount > 0) {
                        speciesList.add(Species.builder()
                                .latMin(lat)
                                .latMax(lat + 5)
                                .lonMin(lon)
                                .lonMax(lon + 5)
                                .speciesCount(speciesCount)
                                .updatedAt(LocalDateTime.now())
                                .build());
                    }

                } catch (Exception e) {
                    log.warn("실패 → 위도 {}~{}, 경도 {}~{}: {}", lat, lat + 5, lon, lon + 5, e.getMessage());
                }

                count++;
                if (count % 100 == 0 || count == totalCells) {
                    int percent = (count * 100) / totalCells;
                    int bars = percent / 5;
                    String progress = "█".repeat(bars) + "-".repeat(20 - bars);
                    log.info("저장중 : [{}] {}% ({} / {})", progress, percent, count, totalCells);
                }
            }
        }

        speciesRepository.deleteAll();
        speciesRepository.saveAll(speciesList);

        log.info("✅ OBIS 해양 생물 종 수 저장 완료: 총 {}개 격자", speciesList.size());
    }
}
