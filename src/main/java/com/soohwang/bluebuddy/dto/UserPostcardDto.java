package com.soohwang.bluebuddy.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPostcardDto {
    private Long userPostcardId;
    private String postcardImagePath;
    private String postcardText;
}
