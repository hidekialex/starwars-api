package com.starwars.api.entity;

import com.starwars.api.caller.dto.FilmDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "episode")
    private Integer episode;

    @Column(name = "openingCrawl", length = 3000)
    private String openingCrawl;

    @Column(name = "director")
    private String director;

    @Column(name = "producer")
    private String producer;

    @Column(name = "releaseDate")
    private String releaseDate;

    @ManyToMany(mappedBy = "films")
    private List<Planet> planets = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "FilmStarship",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id", referencedColumnName = "id"))
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