package com.starwars.api.caller;

import com.starwars.api.caller.dto.FilmDTO;
import com.starwars.api.caller.dto.PlanetDetailDTO;
import com.starwars.api.caller.dto.StarshipDTO;
import com.starwars.api.exception.PlanetNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class StarwarsApiExecutor {

    private StarwarsApiCaller caller;

    StarwarsApiExecutor(StarwarsApiCaller caller) {
        this.caller = caller;
    }

    @Cacheable(value = "planets")
    public PlanetDetailDTO getPlanetByName(String name) throws PlanetNotFoundException {
        return caller.getPlanetByName(name);
    }

    @Cacheable(value = "films")
    public CompletableFuture<FilmDTO> getFilm(String url) {
        return caller.getSomethingById(url, FilmDTO.class);
    }

    @Cacheable(value = "starships")
    public CompletableFuture<StarshipDTO> getStarship(String url) {
        return caller.getSomethingById(url, StarshipDTO.class);
    }
}
