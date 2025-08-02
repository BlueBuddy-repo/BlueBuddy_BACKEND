package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.Pet;
import com.soohwang.bluebuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> getPetByUser(User user);
}
