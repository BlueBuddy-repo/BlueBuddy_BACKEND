package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "sea_creature")
public class SeaCreature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creatureId;

    @NotNull
    private String nameKr;

    @NotNull
    private String nameEn;

    @NotNull
    private String scientificName;

    @NotNull
    private Long password;

    @NotNull
    private String habitat;

    @NotNull
    private Long endangermentLevel;

    @NotNull
    private String description;

//    @OneToMany(mappedBy = "seaCreature", cascade = CascadeType.REMOVE)
//    private List<Pet> pets;

    @OneToMany(mappedBy = "seaCreature", cascade = CascadeType.REMOVE)
    private List<UserCreature> userCreatures;

    public SeaCreature() {}
}

