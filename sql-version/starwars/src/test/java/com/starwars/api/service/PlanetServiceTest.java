package com.starwars.api.service;

import com.starwars.api.caller.StarwarsApiExecutor;
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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlanetServiceTest {

    @InjectMocks
    private PlanetService service;

    @Mock
    private PlanetRepository repository;

    @Mock
    private StarwarsApiExecutor executor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createIfPlanetNotFound() throws Exception {

        String name = "Dagobah";
        CreatePlanetRequest request = new CreatePlanetRequest();
        request.setName(name);

        when(executor.getPlanetByName(eq(name))).thenThrow(new PlanetNotFoundException("Planet not found."));

        try {
            service.create(request);
        } catch (PlanetNotFoundException e) {
            assertEquals("Planet not found.", e.getMessage());
        }

        verify(executor, times(1)).getPlanetByName(eq(name));
        verify(executor, times(0)).getFilm(any(String.class));
        verify(executor, times(0)).getStarship(any(String.class));
    }

    @Test
    public void createSuccess() throws PlanetNotFoundException {

        String name = "Dagobah";
        CreatePlanetRequest request = new CreatePlanetRequest();
        request.setName(name);

        PlanetDetailDTO planetDetailDTO = new PlanetDetailDTO();
        planetDetailDTO.setClimate("climate");
        planetDetailDTO.setName(name);
        planetDetailDTO.setTerrain("terrain");
        planetDetailDTO.setUrl("https://starwars/api/planet/12");

        String film1 = "https://starwars/api/films/1";
        String film2 = "https://starwars/api/films/2";
        planetDetailDTO.setFilms(Arrays.asList(film1, film2));

        String starship1 = "https://starwars/api/starship/1";
        String starship2 = "https://starwars/api/starship/2";
        String starship3 = "https://starwars/api/starship/3";
        String starship4 = "https://starwars/api/starship/4";

        FilmDTO filmDTO1 = new FilmDTO();
        filmDTO1.setDirector("director1");
        filmDTO1.setEpisodeId(1);
        filmDTO1.setOperningCrawl("openingCrawl1");
        filmDTO1.setProducer("producer1");
        filmDTO1.setReleaseDate("releaseDate1");
        filmDTO1.setTitle("title1");
        filmDTO1.setUrl(film1);
        filmDTO1.setStarships(Arrays.asList(starship1, starship2));

        FilmDTO filmDTO2 = new FilmDTO();
        filmDTO2.setDirector("director2");
        filmDTO2.setEpisodeId(2);
        filmDTO2.setOperningCrawl("openingCrawl2");
        filmDTO2.setProducer("producer2");
        filmDTO2.setReleaseDate("releaseDate2");
        filmDTO2.setTitle("title2");
        filmDTO2.setUrl(film2);
        filmDTO2.setStarships(Arrays.asList(starship1, starship2, starship3, starship4));

        StarshipDTO starshipDTO1 = new StarshipDTO();
        starshipDTO1.setName("starship1");
        starshipDTO1.setUrl(starship1);

        StarshipDTO starshipDTO2 = new StarshipDTO();
        starshipDTO2.setName("starship2");
        starshipDTO2.setUrl(starship2);

        StarshipDTO starshipDTO3 = new StarshipDTO();
        starshipDTO3.setName("starship3");
        starshipDTO3.setUrl(starship3);

        StarshipDTO starshipDTO4 = new StarshipDTO();
        starshipDTO4.setName("starship4");
        starshipDTO4.setUrl(starship4);

        when(executor.getPlanetByName(eq(name))).thenReturn(planetDetailDTO);
        when(executor.getFilm(eq(film1))).thenReturn(CompletableFuture.completedFuture(filmDTO1));
        when(executor.getFilm(eq(film2))).thenReturn(CompletableFuture.completedFuture(filmDTO2));
        when(executor.getStarship(eq(starship1))).thenReturn(CompletableFuture.completedFuture(starshipDTO1));
        when(executor.getStarship(eq(starship2))).thenReturn(CompletableFuture.completedFuture(starshipDTO2));
        when(executor.getStarship(eq(starship3))).thenReturn(CompletableFuture.completedFuture(starshipDTO3));
        when(executor.getStarship(eq(starship4))).thenReturn(CompletableFuture.completedFuture(starshipDTO4));

        CreatePlanetResponse response = service.create(request);

        assertEquals(Integer.valueOf(12), response.getId());

        verify(executor, times(1)).getPlanetByName(eq(name));
        verify(executor, times(1)).getFilm(eq(film1));
        verify(executor, times(1)).getFilm(eq(film2));
        verify(executor, times(1)).getStarship(eq(starship1));
        verify(executor, times(1)).getStarship(eq(starship2));
        verify(executor, times(1)).getStarship(eq(starship3));
        verify(executor, times(1)).getStarship(eq(starship4));
        verify(repository, times(1)).save(any(Planet.class));
    }

    @Test
    public void getPlanetByIdSuccess() throws PlanetNotFoundException {

        Integer id = 100;

        Planet planet = buildPlanet(id);

        when(repository.findById(eq(id))).thenReturn(Optional.of(planet));

        PlanetInfoResponse response = service.getPlanetById(id);

        assertEquals(planet.getId(), response.getId());
        assertEquals(planet.getClimate(), response.getClimate());
        assertEquals(planet.getName(), response.getName());
        assertEquals(planet.getTerrain(), response.getTerrain());
        assertEquals(planet.getFilms().get(0).getTitle(), response.getFilms().get(0).getTitle());
        assertEquals(planet.getFilms().get(1).getTitle(), response.getFilms().get(1).getTitle());
        assertEquals(planet.getFilms().get(0).getStarships().get(0).getName(), response.getFilms().get(0).getStarships().get(0).getName());
        assertEquals(planet.getFilms().get(1).getStarships().get(0).getName(), response.getFilms().get(1).getStarships().get(0).getName());
    }

    @Test
    public void getPlanetByIdIfPlanetNotFound() {
        Integer id = 100;
        when(repository.findById(eq(id))).thenReturn(Optional.empty());
        try {
            service.getPlanetById(id);
        } catch (PlanetNotFoundException e) {
            assertEquals("Planet not found.", e.getMessage());
        }
    }

    @Test
    public void getPlanetByNameSuccess() throws Exception {

        Integer id = 100;
        String name = "name";

        Planet planet = buildPlanet(id);
        planet.setName(name);

        when(repository.findByName(eq(name))).thenReturn(Optional.of(planet));

        PlanetInfoResponse response = service.getPlanetByName(name);

        assertEquals(planet.getId(), response.getId());
        assertEquals(planet.getClimate(), response.getClimate());
        assertEquals(planet.getName(), response.getName());
        assertEquals(planet.getTerrain(), response.getTerrain());
        assertEquals(planet.getFilms().get(0).getTitle(), response.getFilms().get(0).getTitle());
        assertEquals(planet.getFilms().get(1).getTitle(), response.getFilms().get(1).getTitle());
        assertEquals(planet.getFilms().get(0).getStarships().get(0).getName(), response.getFilms().get(0).getStarships().get(0).getName());
        assertEquals(planet.getFilms().get(1).getStarships().get(0).getName(), response.getFilms().get(1).getStarships().get(0).getName());
    }

    @Test
    public void getPLanetByNameIfPlanetNotFound() {
        String name = "name";
        when(repository.findByName(eq(name))).thenReturn(Optional.empty());
        try {
            service.getPlanetByName(name);
        } catch (PlanetNotFoundException e) {
            assertEquals("Planet not found.", e.getMessage());
        }
    }

    @Test
    public void removeSuccess() {
        Integer id = 100;
        RemovePlanetResponse response = service.remove(id);
        assertEquals("Planet remove with success.", response.getMsg());
        verify(repository, times(1)).deleteById(eq(id));
    }

    @Test
    public void getAllPlanetsSuccess() {

        Integer page = 0;
        Integer size = 1;

        Planet planet1 = new Planet();
        planet1.setName("planet1");
        Planet planet2 = new Planet();
        planet2.setName("planet1");

        Page<Planet> pagedPlanets = new PageImpl<Planet>(Arrays.asList(planet1, planet2));
        when(repository.findAll(any(Pageable.class))).thenReturn(pagedPlanets);
        PlanetsResponse response = service.getAllPlanets(page, size);

        assertEquals(Integer.valueOf(1), Integer.valueOf(response.getTotalPages()));
        assertEquals(Long.valueOf(2), Long.valueOf(response.getTotalElements()));
        assertEquals(Integer.valueOf(1), Integer.valueOf(response.getSize()));
        assertEquals(planet1.getName(), response.getPlanets().get(0).getName());
        assertEquals(planet2.getName(), response.getPlanets().get(1).getName());
    }

    private Planet buildPlanet(Integer id) {
        Planet planet = new Planet();
        planet.setClimate("climate");
        planet.setId(id);
        planet.setName("name");
        planet.setTerrain("terrain");

        Starship starship1 = new Starship();
        starship1.setName("starship1");

        Starship starship2 = new Starship();
        starship2.setName("starship1");

        Film film1 = new Film();
        film1.setTitle("title1");
        film1.setStarships(Arrays.asList(starship1));

        Film film2 = new Film();
        film2.setTitle("title2");
        film2.setStarships(Arrays.asList(starship2));

        planet.setFilms(Arrays.asList(film1, film2));
        return planet;
    }
}





