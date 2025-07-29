package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.SeaCreature;
import com.soohwang.bluebuddy.entity.User;
import com.soohwang.bluebuddy.entity.UserCreature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCreatureRepository extends JpaRepository<UserCreature, Long> {
    boolean existsByUserAndSeaCreature(User user, SeaCreature creature);
}
