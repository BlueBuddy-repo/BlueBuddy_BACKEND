package com.soohwang.bluebuddy.dto;

import com.soohwang.bluebuddy.entity.SeaCreature;
import lombok.Getter;

@Getter
public class ChangePetDto {
    private Long seaCreatureId;
    private String petName;
}
