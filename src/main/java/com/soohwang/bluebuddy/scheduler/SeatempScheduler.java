package com.soohwang.bluebuddy.scheduler;

import com.soohwang.bluebuddy.crawling.SeatempCrawler;
import com.soohwang.bluebuddy.service.SeatempService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Component
public class SeatempScheduler {

    private final SeatempCrawler seatempCrawler;
    private final SeatempService seatempService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void downloadAndSave() {
        LocalDate targetDate = LocalDate.now().minusDays(3);
        String filePath = seatempCrawler.downloadCsv();

        if (filePath != null) {
            seatempService.saveFromCSV(filePath);
            log.info("수온 데이터 저장 완료 ∼！");
        } else {
            log.warn("수온 파일 경로가 null입니다. 저장 스킵.");
        }
    }
}

