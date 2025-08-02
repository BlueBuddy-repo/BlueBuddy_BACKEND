package com.soohwang.bluebuddy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetDto {
    private String PetName;
    private String PetImage;
    private Long creatureId;
}
