package com.starwars.api.controller;

import com.starwars.api.controller.request.CreatePlanetRequest;
import com.starwars.api.controller.response.CreatePlanetResponse;
import com.starwars.api.controller.response.PlanetInfoResponse;
import com.starwars.api.controller.response.PlanetsResponse;
import com.starwars.api.controller.response.RemovePlanetResponse;
import com.starwars.api.exception.PlanetNotFoundException;
import com.starwars.api.service.PlanetService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService service;

    PlanetController(PlanetService service) {
        this.service = service;
    }

    @PostMapping
    public CreatePlanetResponse create(@RequestBody @Valid CreatePlanetRequest request) throws PlanetNotFoundException {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public PlanetInfoResponse getPlanetById(@PathVariable Integer id) throws PlanetNotFoundException {
        return service.getPlanetById(id);
    }

    @GetMapping("/name/{name}")
    public PlanetInfoResponse getPlanetByName(@PathVariable String name) throws PlanetNotFoundException {
        return service.getPlanetByName(name);
    }

    @DeleteMapping("/{id}")
    public RemovePlanetResponse remove(@PathVariable Integer id) {
        return service.remove(id);
    }

    @GetMapping
    public PlanetsResponse getAllPlanets(@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "page", required = false) Integer page) {
        return service.getAllPlanets(size, page);
    }
}