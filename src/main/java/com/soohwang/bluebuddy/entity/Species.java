package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "species")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int latMin;

    @Column
    private int latMax;

    @Column
    private int lonMin;

    @Column
    private int lonMax;

    @Column
    private int speciesCount;

    @Column
    private LocalDateTime updatedAt;
}
