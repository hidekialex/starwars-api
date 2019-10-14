package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starwars.api.entity.Film;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FilmResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("episode")
    private Integer episode;

    @JsonProperty("openingCrawl")
    private String openingCrawl;

    @JsonProperty("director")
    private String director;

    @JsonProperty("producer")
    private String producer;

    @JsonProperty("releaseDate")
    private String releaseDate;

    @JsonProperty("starships")
    private List<StarshipResponse> starships;

    public static FilmResponse of(Film film) {

        FilmResponse response = new FilmResponse();
        response.setDirector(film.getDirector());
        response.setEpisode(film.getEpisode());
        response.setOpeningCrawl(film.getOpeningCrawl());
        response.setProducer(film.getProducer());
        response.setReleaseDate(film.getReleaseDate());
        response.setTitle(film.getTitle());

        List<StarshipResponse> starships = film.getStarships()
                .stream()
                .map(StarshipResponse::of)
                .collect(Collectors.toList());

        response.setStarships(starships);
        return response;
    }
}
