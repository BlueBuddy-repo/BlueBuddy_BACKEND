package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "sea_creature")
public class SeaCreature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creature_id")
    private Long creatureId;

    @Column(name = "name_kr", length = 100)
    private String nameKr;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "length")
    private Long password;

    @Column(name = "habitat")
    private String habitat;

    @Column(name = "endangerment_level")
    private Long endangermentLevel;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "seaCreature", cascade = CascadeType.REMOVE)
//    private List<Pet> pets;

    @OneToMany(mappedBy = "seaCreature", cascade = CascadeType.REMOVE)
    private List<UserCreature> userCreatures;

    public SeaCreature() {}
}

