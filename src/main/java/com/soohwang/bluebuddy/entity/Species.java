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

    @Column(name = "lat_min")
    private int latMin;

    @Column(name = "lat_max")
    private int latMax;

    @Column(name = "lon_min")
    private int lonMin;

    @Column(name = "lon_max")
    private int lonMax;

    @Column(name = "species_count")
    private int speciesCount;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
