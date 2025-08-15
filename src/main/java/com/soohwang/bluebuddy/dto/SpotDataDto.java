package com.soohwang.bluebuddy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotDataDto {
    private String spotId;
    private String spotName;
    private Boolean isCompleted;
    private int missionCount; //사용자가 수행한 제로웨이스트 행동 수
    private int latitude;
    private int longitude;
    private Boolean isOpened;
}
