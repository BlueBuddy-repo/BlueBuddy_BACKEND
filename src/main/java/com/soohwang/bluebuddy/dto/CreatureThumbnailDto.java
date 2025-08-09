package com.soohwang.bluebuddy.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatureThumbnailDto {
    private Long creatureId;
    private Long displayOrder;
    private String imageUrl;
}
