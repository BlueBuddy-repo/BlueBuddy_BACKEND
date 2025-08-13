package com.soohwang.bluebuddy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
public class UserCreature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCreatureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creature_id")
    @JsonBackReference
    private SeaCreature seaCreature;

    @NotNull
    private String petName;

    @NotNull
    private boolean selected;

    public UserCreature() {
    }
}

