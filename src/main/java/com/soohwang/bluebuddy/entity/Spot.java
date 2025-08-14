package com.soohwang.bluebuddy.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    @NotNull
    private String spotName;

    @NotNull
    private int latitude;

    @NotNull
    private int longitude;

    @NotNull
    private Long missionNum;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeaCreature> seaCreatures = new ArrayList<>();
}
