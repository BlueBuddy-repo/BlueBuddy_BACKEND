package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.SeaCreature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeaCreatureRepository extends JpaRepository<SeaCreature, String> {
    List<SeaCreature> findByHabitat(String habitat);

    @Query(value = "SELECT * FROM sea_creature ORDER BY RAND() LIMIT 1", nativeQuery = true)
    SeaCreature findRandomOne();

}
