package com.soohwang.bluebuddy.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatureDto {
    private Long creatureId;
    private String nameKr;
    private String nameEn;
    private String scientificName;
    private String habitat;
    private Long endangermentLevel;
    private String description;
    private Long displayOrder;
}
