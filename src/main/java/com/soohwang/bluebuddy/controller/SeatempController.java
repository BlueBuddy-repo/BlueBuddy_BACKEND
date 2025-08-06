package com.soohwang.bluebuddy.controller;

import com.soohwang.bluebuddy.crawling.SeatempCrawler;
import com.soohwang.bluebuddy.crawling.SeatempCsvParser;
import com.soohwang.bluebuddy.dto.SeatempDto;
import com.soohwang.bluebuddy.entity.Seatemp;
import com.soohwang.bluebuddy.exception.ApiResponse;
import com.soohwang.bluebuddy.service.SeatempService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SeatempController {

    private final SeatempCrawler crawler;
    private final SeatempCsvParser parser;
    private final SeatempService service;


    @GetMapping("/api/seatemp")
    public ResponseEntity<ApiResponse> getAllSeatemp() {
        List<SeatempDto> data = service.getAllDto();

        if (data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse(false, "수온 데이터 없음", null));
        }

        return ResponseEntity.ok(
                new ApiResponse(true, "수온 정보 조회 성공", data)
        );
    }
    @GetMapping("/api/seatemp/crawl")
    public ResponseEntity<ApiResponse> testCrawl() {
        String path = crawler.downloadCsv();

        if (path == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "CSV 다운로드 실패", null));
        }

        List<Seatemp> parsed = parser.parse(path);
        service.saveAll(parsed);

        return ResponseEntity.ok(
                new ApiResponse(true, "수온 데이터 크롤링 및 저장 성공", parsed.size() + "건 저장됨")
        );
    }

}
