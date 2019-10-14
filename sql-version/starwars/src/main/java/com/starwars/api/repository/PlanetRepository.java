package com.starwars.api.repository;

import com.starwars.api.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {

    Optional<Planet> findByName(String name);
}
