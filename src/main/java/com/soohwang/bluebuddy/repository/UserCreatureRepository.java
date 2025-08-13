package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserCreature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserCreatureRepository extends JpaRepository<UserCreature, Long> {
    boolean existsByUserAndSeaCreature(User user, SeaCreature creature);

    @Query("select uc.seaCreature from UserCreature uc where uc.user = :user")
    List<SeaCreature> findSeaCreaturesByUser(@Param("user") User user);

    Optional<UserCreature> findByUserAndSeaCreature(User user, SeaCreature seaCreature);

    Optional<UserCreature> findByUser_UserIdAndSelectedTrue(Long userId);

    List<UserCreature> findAllByUser_UserIdOrderBySelectedDesc(Long userId);}
