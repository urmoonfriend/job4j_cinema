package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.model.response.SessionResponse;
import kz.job4j.cinema.repository.impl.Sql2oFilmSessionRepository;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import kz.job4j.cinema.service.impl.HallServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmSessionServiceTest {
    private FilmSessionServiceImpl filmSessionService;
    private FilmServiceImpl filmService;
    private HallServiceImpl hallService;
    private Sql2oFilmSessionRepository filmSessionRepository;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmServiceImpl.class);
        filmSessionRepository = mock(Sql2oFilmSessionRepository.class);
        hallService = mock(HallServiceImpl.class);
        filmSessionService = new FilmSessionServiceImpl(filmSessionRepository, filmService, hallService);
    }

    @Test
    public void whenRequestFindByIdThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        Optional<FilmResponse> filmResponse = Optional.of(new FilmResponse(
                "name", "description", 2000, "genre",
                16, 200, 0));
        Optional<Hall> hallOptional = Optional.of(new Hall(0, "hall", 4,
                4, "hall"));
        Optional<FilmSession> filmSession = Optional.of(new FilmSession(0, 0, 0,
                dateTime, dateTime, 2000));

        SessionResponse sessionResponse = new SessionResponse(0, filmResponse.get(),
                hallOptional.get(), dateTime, dateTime, 2000
        );
        when(filmSessionRepository.findById(0)).thenReturn(filmSession);
        when(filmService.findById(0)).thenReturn(filmResponse);
        when(hallService.findById(0)).thenReturn(hallOptional);
        var result = filmSessionService.findById(0);
        assertThat(result).isEqualTo(Optional.of(sessionResponse));
    }

    @Test
    public void whenRequestFindByIdThenNotFound() {
        when(filmSessionRepository.findById(0)).thenReturn(Optional.empty());
        var result = filmSessionService.findById(0);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenRequestSaveThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmSession filmSession = new FilmSession(0, 0, 0,
                dateTime, dateTime, 2000);
        when(filmSessionRepository.save(filmSession)).thenReturn(filmSession);
        var result = filmSessionService.save(filmSession);
        assertThat(result).isEqualTo(filmSession);
    }

    @Test
    public void whenRequestFindAllThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        Optional<FilmResponse> filmResponse = Optional.of(new FilmResponse(
                "name", "description", 2000, "genre",
                16, 200, 0));
        Optional<Hall> hallOptional = Optional.of(new Hall(0, "hall", 4,
                4, "hall"));
        FilmSession filmSession = new FilmSession(0, 0, 0,
                dateTime, dateTime, 2000);
        SessionResponse sessionResponse = new SessionResponse(0, filmResponse.get(),
                hallOptional.get(), dateTime, dateTime, 2000
        );

        Optional<FilmResponse> filmResponse1 = Optional.of(new FilmResponse(
                "name1", "description", 2000, "genre",
                16, 200, 0));
        Optional<Hall> hallOptional1 = Optional.of(new Hall(1, "hall", 4,
                4, "hall"));
        FilmSession filmSession1 = new FilmSession(1, 1, 1,
                dateTime.plusDays(1), dateTime.plusDays(1), 2000);
        SessionResponse sessionResponse1 = new SessionResponse(1, filmResponse1.get(),
                hallOptional1.get(), dateTime.plusDays(1), dateTime.plusDays(1), 2000
        );
        when(filmService.findById(0)).thenReturn(filmResponse);
        when(hallService.findById(0)).thenReturn(hallOptional);

        when(filmService.findById(1)).thenReturn(filmResponse1);
        when(hallService.findById(1)).thenReturn(hallOptional1);
        when(filmSessionRepository.findAll()).thenReturn(List.of(filmSession, filmSession1));
        var result = filmSessionService.findAll();
        assertThat(result).isEqualTo(List.of(sessionResponse, sessionResponse1));
    }

}
