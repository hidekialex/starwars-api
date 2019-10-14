package com.starwars.api.repository;

import com.starwars.api.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {

/*    void create(Planet planet);

    Optional<Planet> getById(String id);

    Optional<Planet> getByName(String name);

    void remove(String id);

    List<Planet> getPlanets();*/
}
