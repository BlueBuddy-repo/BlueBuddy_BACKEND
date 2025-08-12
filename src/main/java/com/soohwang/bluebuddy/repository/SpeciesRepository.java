package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    List<Species> findAllSpeciesByLatMinGreaterThanEqualAndLatMaxLessThanEqualAndLonMinGreaterThanEqualAndLonMaxLessThanEqual(
            int latMin, int latMax, int lonMin, int lonMax
    );

    default List<Species> findAllSpecies() {
        return findAll();
    }
}


