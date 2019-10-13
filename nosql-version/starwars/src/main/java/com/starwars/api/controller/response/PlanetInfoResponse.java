package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starwars.api.entity.Planet;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlanetInfoResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("climate")
    private String climate;

    @JsonProperty("terrain")
    private String terrain;

    @JsonProperty("films")
    private List<FilmResponse> films;

    public static PlanetInfoResponse  of(Planet planet) {
        PlanetInfoResponse response = new PlanetInfoResponse();
        response.setClimate(planet.getClimate());
        response.setId(planet.getId());
        response.setName(planet.getName());
        response.setTerrain(planet.getTerrain());
        List<FilmResponse> films = planet.getFilms()
                .stream()
                .map(FilmResponse::of)
                .collect(Collectors.toList());
        response.setFilms(films);
        return response;
    }
}
