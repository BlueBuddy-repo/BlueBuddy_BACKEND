package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.dto.CreatureByHabitatDto;
import com.soohwang.bluebuddy.entity.SeaCreature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SeaCreatureRepository extends JpaRepository<SeaCreature, Long> {
    List<SeaCreature> findBySpot_SpotId(Long spotId);

    Optional<SeaCreature> getSeaCreatureByCreatureId(Long creatureId);

    @Query("SELECT new com.soohwang.bluebuddy.dto.CreatureByHabitatDto(c.creatureId, c.nameKr) " +
            "FROM SeaCreature c WHERE c.spot.spotId = :spotId")
    List<CreatureByHabitatDto> findCreatureDtoBySpotId(@Param("spotId") Long spotId);
}
