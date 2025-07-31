package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @OneToOne
    @JoinColumn(name = "user_id") // FK
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creature_id", nullable = false)
    private SeaCreature seaCreature;

    private String petName;
}
