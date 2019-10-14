package com.starwars.api.entity;

import com.starwars.api.caller.dto.PlanetDetailDTO;
import com.starwars.api.utils.RegexUtils;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "climate")
    private String climate;

    @Column(name = "terrain")
    private String terrain;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PlanetFilm",
            joinColumns = @JoinColumn(name = "planet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private List<Film> films;

    public static Planet of(PlanetDetailDTO dto, List<Film> films) {
        Planet planet = new Planet();
        planet.setClimate(dto.getClimate());
        planet.setName(dto.getName());
        planet.setTerrain(dto.getTerrain());
        planet.setFilms(films);

        RegexUtils.extractIdForUrl(dto.getUrl())
                .ifPresent(id -> planet.setId(id));
        return planet;
    }
}