package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.SeaCreature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeaCreatureRepository extends JpaRepository<SeaCreature, String> {
    List<SeaCreature> findByHabitat(String habitat);
}
