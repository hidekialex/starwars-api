package com.starwars.api.caller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FilmDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("episode_id")
    private Integer episodeId;

    @JsonProperty("opening_crawl")
    private String operningCrawl;

    @JsonProperty("director")
    private String director;

    @JsonProperty("producer")
    private String producer;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("starships")
    private List<String> starships;
}