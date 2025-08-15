package com.soohwang.bluebuddy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RandomCreatureDto {
    private String name;
    private String imageUrl;
}
