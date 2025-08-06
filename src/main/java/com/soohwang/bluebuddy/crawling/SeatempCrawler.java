package com.soohwang.bluebuddy.crawling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

@Slf4j
@Component
public class SeatempCrawler {

    public String downloadCsv() {
        // 데이터가 존재하는 안전한 날짜 기준 (예: 3일 전)
        LocalDate date = LocalDate.now().minusDays(3);
        String dateStr = date.toString();

        String downloadUrl = "https://pae-paha.pacioos.hawaii.edu/erddap/griddap/dhw_5km.csv?CRW_SST[("
                + dateStr + ")][1000:100:2600][1000:100:2600]";
        String filePath = "src/main/resources/data/sea_temp_" + dateStr + ".csv";

        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadUrl).openStream());
             FileOutputStream out = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            log.info("데이터 저장 성공: {}", filePath);
            return filePath;

        } catch (IOException e) {
            log.error("데이터 저장 실패: {}", e.getMessage(), e);
            return null;
        }
    }
}
