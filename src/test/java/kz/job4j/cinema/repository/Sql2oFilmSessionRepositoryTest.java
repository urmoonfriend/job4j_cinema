package kz.job4j.cinema.repository;

import kz.job4j.cinema.configuration.DatasourceConfiguration;
import kz.job4j.cinema.model.entity.*;
import kz.job4j.cinema.repository.impl.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmSessionRepositoryTest {
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oHallRepository sql2oHallRepository;
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static File file;

    private static Film film;
    private static Genre genre;

    private static Hall hall;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        hall = sql2oHallRepository.save(new Hall(1, "hall", 5, 5, "hall"));
        file = sql2oFileRepository.save(new File("test", "test"));
        genre = sql2oGenreRepository.save(new Genre("drama"));
        film = sql2oFilmRepository.save(new Film("film", "description", 2023, genre.getId(), 18, 180, file.getId()));
    }

    @AfterEach
    public void clearFilmSessions() {
        var filmSessions = sql2oFilmSessionRepository.findAll();
        for (var filmSession : filmSessions) {
            sql2oFilmSessionRepository.deleteById(filmSession.getId());
        }
    }

    @AfterAll
    public static void deleteSaved() {
        sql2oFilmSessionRepository.deleteAll();
        sql2oFilmRepository.deleteAll();
        sql2oFileRepository.deleteAll();
        sql2oHallRepository.deleteAll();
        sql2oGenreRepository.deleteAll();
    }

    @Test
    public void whenSaveThenGetSame() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(0, film.getId(), hall.getId(), dateTime, dateTime, 1000));
        var savedFilmSession = sql2oFilmSessionRepository.findById(filmSession.getId()).get();
        assertThat(savedFilmSession).usingRecursiveComparison().isEqualTo(filmSession);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        var filmSession1 = sql2oFilmSessionRepository.save(new FilmSession(1, film.getId(), hall.getId(), dateTime, dateTime, 1000));
        var filmSession2 = sql2oFilmSessionRepository.save(new FilmSession(2, film.getId(), hall.getId(), dateTime.minusDays(1), dateTime.minusDays(1), 1000));
        var filmSession3 = sql2oFilmSessionRepository.save(new FilmSession(3, film.getId(), hall.getId(), dateTime.plusDays(1), dateTime.plusDays(1), 1000));
        var result = sql2oFilmSessionRepository.findAll();
        assertThat(result).isEqualTo(List.of(filmSession1, filmSession2, filmSession3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oFilmSessionRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oFilmSessionRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(4, film.getId(), hall.getId(), dateTime, dateTime, 1000));
        sql2oFilmSessionRepository.deleteById(filmSession.getId());
        var foundedFilmSession = sql2oFilmSessionRepository.findById(filmSession.getId());
        assertThat(foundedFilmSession).isEqualTo(empty());
    }

}
