package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.SeaCreature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeaCreatureRepository extends JpaRepository<SeaCreature, Long> {
    List<SeaCreature> findByHabitat(String habitat);

    Optional<SeaCreature> getSeaCreatureByCreatureId(Long creatureId);
}
