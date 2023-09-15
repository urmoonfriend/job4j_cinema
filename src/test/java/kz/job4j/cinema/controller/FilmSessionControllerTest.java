package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.model.response.SessionResponse;
import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import org.h2.engine.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmSessionControllerTest {

    private FilmSessionServiceImpl filmSessionService;
    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionServiceImpl.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestGetAllThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmResponse filmResponse = new FilmResponse(
                "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        Hall hall = new Hall(0, "hall", 5, 5, "hall");
        SessionResponse sessionResponse1 = new SessionResponse(0, filmResponse, hall, dateTime, dateTime, 16);
        SessionResponse sessionResponse2 = new SessionResponse(1, filmResponse, hall, dateTime.plusDays(1), dateTime.plusDays(1), 16);
        var sessionResponses = List.of(sessionResponse1, sessionResponse2);
        when(filmSessionService.findAll()).thenReturn(sessionResponses);
        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var sessions = model.getAttribute("sessions");
        assertThat(view).isEqualTo("sessions/list");
        assertThat(sessions).isEqualTo(sessionResponses);
    }

    @Test
    public void whenRequestGetOneThenNotFound() {
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = filmSessionController.getById(model, 1);
        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenRequestGetOneThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmResponse filmResponse = new FilmResponse(
                "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        Hall hall = new Hall(0, "hall", 5, 5, "hall");
        SessionResponse sessionResponse1 = new SessionResponse(0, filmResponse, hall, dateTime, dateTime, 16);
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.of(sessionResponse1));
        var model = new ConcurrentModel();
        var view = filmSessionController.getById(model, 1);
        var filmSession = model.getAttribute("filmSession");
        assertThat(view).isEqualTo("sessions/one");
        assertThat(filmSession).isEqualTo(sessionResponse1);
    }
}
