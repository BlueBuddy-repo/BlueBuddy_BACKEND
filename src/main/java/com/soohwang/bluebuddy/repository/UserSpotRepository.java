package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.Spot;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserSpot;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSpotRepository extends JpaRepository<UserSpot, Long> {
    Optional<UserSpot> findTopByUser_UserIdOrderBySpot_SpotIdDesc(Long user_userId);
    @Query("SELECT us FROM UserSpot us WHERE us.user = :user AND us.spot = :spot")
    Optional<UserSpot> findByUserAndSpot(@Param("user") User user, @Param("spot") Spot spot);
}
