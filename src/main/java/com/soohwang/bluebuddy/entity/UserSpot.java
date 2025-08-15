package com.soohwang.bluebuddy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserSpotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    @JsonBackReference
    private Spot spot;

    @NotNull
    private int missionCount;

    @NotNull
    private Boolean isCompleted;

    @NotNull
    private Boolean isOpened;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sea_creature_id")
    @JsonBackReference
    private SeaCreature seaCreature;

    public void increaseCompletedMission() {
        this.missionCount++;
    }
}
