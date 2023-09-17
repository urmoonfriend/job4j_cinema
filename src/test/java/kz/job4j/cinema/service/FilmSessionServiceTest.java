package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmSessionRepository;
import kz.job4j.cinema.repository.impl.Sql2oGenreRepository;
import kz.job4j.cinema.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    
}
