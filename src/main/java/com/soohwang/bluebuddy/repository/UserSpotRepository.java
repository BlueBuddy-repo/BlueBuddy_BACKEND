package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSpotRepository extends JpaRepository<UserSpot, Long> {
    Optional<UserSpot> findTopByUser_UserIdOrderBySpot_SpotIdDesc(Long user_userId);
}
