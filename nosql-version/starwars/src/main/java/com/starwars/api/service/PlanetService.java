package com.starwars.api.service;

import com.starwars.api.caller.StarwarsApiExecutor;
import com.starwars.api.caller.dto.FilmDTO;
import com.starwars.api.caller.dto.PlanetDetailDTO;
import com.starwars.api.caller.dto.StarshipDTO;
import com.starwars.api.controller.request.CreatePlanetRequest;
import com.starwars.api.controller.response.CreatePlanetResponse;
import com.starwars.api.controller.response.PlanetInfoResponse;
import com.starwars.api.controller.response.PlanetsResponse;
import com.starwars.api.controller.response.RemovePlanetResponse;
import com.starwars.api.entity.Film;
import com.starwars.api.entity.PagedPlanets;
import com.starwars.api.entity.Planet;
import com.starwars.api.entity.Starship;
import com.starwars.api.exception.PlanetNotFoundException;
import com.starwars.api.repository.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    private PlanetRepository repository;

    private StarwarsApiExecutor starwarsApiExecutor;

    PlanetService(PlanetRepository repository, StarwarsApiExecutor starwarsApiExecutor) {
        this.repository = repository;
        this.starwarsApiExecutor = starwarsApiExecutor;
    }

    public CreatePlanetResponse create(CreatePlanetRequest request) throws PlanetNotFoundException {
        PlanetDetailDTO planetDetailDTO = starwarsApiExecutor.getPlanetByName(request.getName());

        List<FilmDTO> films = planetDetailDTO.getFilms()
                .stream()
                .map(f -> starwarsApiExecutor.getFilm(f))
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, StarshipDTO> starships = films.stream()
                .flatMap(f -> Arrays.stream(f.getStarships().toArray()))
                .distinct()
                .map(s -> starwarsApiExecutor.getStarship(s.toString()))
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(StarshipDTO::getUrl, s -> s));

        List<Film> mappedFilms = films.stream()
                .map(f -> {
                    List<Starship> mappedStarships = f.getStarships()
                        .stream()
                        .map(s -> Optional.ofNullable(starships.get(s)))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Starship::of)
                        .collect(Collectors.toList());
                    return Film.of(f, mappedStarships);
                }).collect(Collectors.toList());

        Planet planet = Planet.of(planetDetailDTO, mappedFilms);
        repository.create(planet);
        CreatePlanetResponse response = new CreatePlanetResponse();
        response.setId(planet.getId());
        return response;
    }

    public PlanetInfoResponse getPlanetById(String id) throws PlanetNotFoundException {
        return repository.getById(id)
                .map(PlanetInfoResponse::of)
                .orElseThrow(() -> new PlanetNotFoundException("Planet not found."));
    }

    public PlanetInfoResponse getPlanetByName(String name) throws PlanetNotFoundException {
        return repository.getByName(name)
                .map(PlanetInfoResponse::of)
                .orElseThrow(() -> new PlanetNotFoundException("Planet not found."));
    }

    public RemovePlanetResponse remove(String id) {
        repository.remove(id);
        RemovePlanetResponse response = new RemovePlanetResponse();
        response.setMsg("Planet remove with success.");
        return response;
    }

    public PlanetsResponse getAllPlanets(Integer size, String lastKey) {
        PagedPlanets pagedPlanets = repository.getPlanets(size, Optional.ofNullable(lastKey));
        List<PlanetInfoResponse> planetsInfo = pagedPlanets.getPlanets()
                .stream()
                .map(PlanetInfoResponse::of)
                .collect(Collectors.toList());
        return PlanetsResponse.of(pagedPlanets.getLastKey(), planetsInfo);
    }
}