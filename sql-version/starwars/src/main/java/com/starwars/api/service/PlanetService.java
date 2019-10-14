package com.starwars.api.service;

import com.starwars.api.caller.StarwarsApiCaller;
import com.starwars.api.caller.dto.FilmDTO;
import com.starwars.api.caller.dto.PlanetDetailDTO;
import com.starwars.api.caller.dto.StarshipDTO;
import com.starwars.api.controller.request.CreatePlanetRequest;
import com.starwars.api.controller.response.CreatePlanetResponse;
import com.starwars.api.controller.response.PlanetInfoResponse;
import com.starwars.api.controller.response.PlanetsResponse;
import com.starwars.api.controller.response.RemovePlanetResponse;
import com.starwars.api.entity.Film;
import com.starwars.api.entity.Planet;
import com.starwars.api.entity.Starship;
import com.starwars.api.exception.PlanetNotFoundException;
import com.starwars.api.repository.PlanetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    private PlanetRepository repository;

    private StarwarsApiCaller starwarsApiCaller;

    PlanetService(PlanetRepository repository, StarwarsApiCaller starwarsApiCaller) {
        this.repository = repository;
        this.starwarsApiCaller = starwarsApiCaller;
    }

    public CreatePlanetResponse create(CreatePlanetRequest request) throws PlanetNotFoundException {
        PlanetDetailDTO planetDetailDTO = starwarsApiCaller.getPlanetByName(request.getName());

        List<FilmDTO> films = planetDetailDTO.getFilms()
                .stream()
                .map(f -> starwarsApiCaller.getSomethingById(f, FilmDTO.class))
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, StarshipDTO> starships = films.stream()
                .flatMap(f -> Arrays.stream(f.getStarships().toArray()))
                .distinct()
                .map(s -> starwarsApiCaller.getSomethingById(s.toString(), StarshipDTO.class))
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
        repository.save(planet);
        CreatePlanetResponse response = new CreatePlanetResponse();
        response.setId(planet.getId());
        return response;
    }

    public PlanetInfoResponse getPlanetById(Integer id) throws PlanetNotFoundException {
        return repository.findById(id)
                .map(PlanetInfoResponse::of)
                .orElseThrow(() -> new PlanetNotFoundException(HttpStatus.NOT_FOUND));
    }

    public PlanetInfoResponse getPlanetByName(String name) throws PlanetNotFoundException {
        return repository.findByName(name)
                .map(PlanetInfoResponse::of)
                .orElseThrow(() -> new PlanetNotFoundException(HttpStatus.NOT_FOUND));
    }

    public RemovePlanetResponse remove(Integer id) {
        repository.deleteById(id);
        RemovePlanetResponse response = new RemovePlanetResponse();
        response.setMsg("Planet remove with success.");
        return response;
    }

    public PlanetsResponse getAllPlanets(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Planet> pagedPlanets = repository.findAll(pageable);

        List<PlanetInfoResponse> planetsInfoResponse = pagedPlanets
                .stream()
                .map(PlanetInfoResponse::of)
                .collect(Collectors.toList());

        PlanetsResponse response = new PlanetsResponse();
        response.setPlanets(planetsInfoResponse);
        response.setTotalElements(pagedPlanets.getTotalElements());
        response.setPage(page);
        response.setSize(size);
        response.setTotalPages(pagedPlanets.getTotalPages());
        return response;
    }
}