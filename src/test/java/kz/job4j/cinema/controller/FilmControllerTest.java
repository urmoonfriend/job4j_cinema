package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmControllerTest {

    private FilmServiceImpl filmService;
    private FilmController filmController;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmServiceImpl.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenRequestGetAllThenOk() {
        FilmResponse filmResponse1 = new FilmResponse(
                 "film1", "film1", 2000, "genre1", 16, 180, 0
        );
        FilmResponse filmResponse2 = new FilmResponse(
                 "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        var filmResponses = List.of(filmResponse1, filmResponse2);
        when(filmService.findAll()).thenReturn(filmResponses);
        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var films = model.getAttribute("films");
        assertThat(view).isEqualTo("films/list");
        assertThat(films).isEqualTo(filmResponses);
    }

    @Test
    public void whenRequestGetOneThenNotFound() {
        when(filmService.findById(any(Integer.class))).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = filmController.getById(model, 1);
        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenRequestGetOneThenOk() {
        FilmResponse filmResponse1 = new FilmResponse(
                 "film1", "film1", 2000, "genre1", 16, 180, 0
        );
        when(filmService.findById(any(Integer.class))).thenReturn(Optional.of(filmResponse1));
        var model = new ConcurrentModel();
        var view = filmController.getById(model, 1);
        var film = model.getAttribute("film");
        assertThat(view).isEqualTo("films/one");
        assertThat(film).isEqualTo(filmResponse1);
    }
}
