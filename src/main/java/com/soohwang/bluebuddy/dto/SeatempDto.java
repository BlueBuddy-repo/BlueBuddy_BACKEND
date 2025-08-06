package com.soohwang.bluebuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatempDto {
    private double lat;         //위도
    private double lng;         //경도
    private int level;
    private String time;       // 지역 이름
}

