package com.soohwang.bluebuddy.dto;

import com.soohwang.bluebuddy.entity.SeaCreature;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCreatureDto {
    private String nameKr;

    public static NewCreatureDto fromEntity(SeaCreature creature) {
        return NewCreatureDto.builder()
                .nameKr(creature.getNameKr())
                .build();
    }
}
