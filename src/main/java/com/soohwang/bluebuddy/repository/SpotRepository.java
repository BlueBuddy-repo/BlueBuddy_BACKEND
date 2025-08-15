package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.Spot;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    @Query("SELECT s.missionNum FROM Spot s WHERE s.spotId = :spotId")
    Integer getMissionNumBySpotId(@Param("spotId") Long spotId);
}
