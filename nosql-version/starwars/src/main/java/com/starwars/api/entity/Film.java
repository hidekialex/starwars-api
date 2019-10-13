package com.starwars.api.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.starwars.api.caller.dto.FilmDTO;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBDocument
@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "episode")
    private Integer episode;

    @DynamoDBAttribute(attributeName = "openingCrawl")
    private String openingCrawl;

    @DynamoDBAttribute(attributeName = "director")
    private String director;

    @DynamoDBAttribute(attributeName = "producer")
    private String producer;

    @DynamoDBAttribute(attributeName = "releaseDate")
    private String releaseDate;

    @DynamoDBAttribute(attributeName = "starships")
    private List<Starship> starships;

    public static Film of(FilmDTO dto, List<Starship> starships) {
        Film film = new Film();
        film.setDirector(dto.getDirector());
        film.setEpisode(dto.getEpisodeId());
        film.setOpeningCrawl(dto.getOperningCrawl());
        film.setProducer(dto.getProducer());
        film.setReleaseDate(dto.getReleaseDate());
        film.setTitle(dto.getTitle());
        film.setStarships(starships);
        return film;
    }
}