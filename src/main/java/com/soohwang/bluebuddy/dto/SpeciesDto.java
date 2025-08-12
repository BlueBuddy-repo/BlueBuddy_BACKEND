
package com.soohwang.bluebuddy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SpeciesDto {

    private Long id;
    private int latMin;
    private int latMax;
    private int lonMin;
    private int lonMax;
    private int speciesCount;
    private LocalDateTime updatedAt;
}