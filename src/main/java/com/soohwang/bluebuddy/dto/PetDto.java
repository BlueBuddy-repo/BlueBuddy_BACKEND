package com.soohwang.bluebuddy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetDto {
    private Long userCreatureId;
    private String petName;
    private String petImage;
    private Long creatureId;
}
