package com.starwars.api.entity;

import com.starwars.api.caller.dto.StarshipDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @ManyToMany(mappedBy = "starships")
    private List<Film> films = new ArrayList<>();

    public static Starship of(StarshipDTO dto) {
        Starship starship = new Starship();
        starship.setManufacturer(dto.getManufacturer());
        starship.setModel(dto.getModel());
        starship.setName(dto.getName());
        return starship;
    }
}
