package com.starwars.api.repository;

import com.starwars.api.entity.PagedPlanets;
import com.starwars.api.entity.Planet;

import java.util.Optional;

public interface PlanetRepository {

    void create(Planet planet);

    Optional<Planet> getById(String id);

    Optional<Planet> getByName(String name);

    void remove(String id);

    PagedPlanets getPlanets(Integer size, Optional<String> startKey);
}
