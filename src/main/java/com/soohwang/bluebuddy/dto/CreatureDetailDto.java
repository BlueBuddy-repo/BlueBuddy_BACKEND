package com.soohwang.bluebuddy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatureDetailDto {
    private Long creatureId;
    private String nameKr;
    private String nameEn;
    private String scientificName;
    private Long endangermentLevel;
    private String description;
    private String imageUrl;
}
