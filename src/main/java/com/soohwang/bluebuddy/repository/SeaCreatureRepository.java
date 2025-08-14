package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.dto.CreatureByHabitatDto;
import com.soohwang.bluebuddy.entity.SeaCreature;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeaCreatureRepository extends JpaRepository<SeaCreature, Long> {
    List<SeaCreature> findByHabitat(String habitat);

    Optional<SeaCreature> getSeaCreatureByCreatureId(Long creatureId);

    @Query("SELECT new com.soohwang.bluebuddy.dto.CreatureByHabitatDto(c.creatureId, c.nameKr) " +
            "FROM SeaCreature c WHERE c.habitat = :habitat")
    List<CreatureByHabitatDto> findCreatureDtoByHabitat(@Param("habitat") String habitat);
}
