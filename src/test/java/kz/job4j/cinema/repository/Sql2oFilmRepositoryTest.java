package kz.job4j.cinema.repository;

import kz.job4j.cinema.configuration.DatasourceConfiguration;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.repository.impl.Sql2oGenreRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    private static Sql2oFileRepository sql2oFileRepository;

    private static Sql2oGenreRepository sql2oGenreRepository;

    private static File file;

    private static Genre genre;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);

        sql2oFilmRepository.deleteAll();
        sql2oGenreRepository.deleteAll();
        sql2oFileRepository.deleteAll();

        file = sql2oFileRepository.save(new File("test", "test"));
        genre =  sql2oGenreRepository.save(new Genre("drama"));
    }

    @AfterAll
    public static void deleteSaved() {
        sql2oFilmRepository.deleteAll();
        sql2oFileRepository.deleteAll();
        sql2oGenreRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Collection<Film> films = sql2oFilmRepository.findAll();
        films.stream().forEach(
                film -> {
                    System.out.println();
                    System.out.println("[");
                    System.out.println(film.getId());
                    System.out.println(film.getName());
                    System.out.println(film.getFileId());
                    System.out.println("]");
                    System.out.println();
                }
        );
        assertThat(films.size()).isEqualTo(0);
    }

    @AfterEach
    public void clearFilms() {
        var films = sql2oFilmRepository.findAll();
        for (var film : films) {
            sql2oFilmRepository.deleteById(film.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var film = sql2oFilmRepository.save(new Film("name", "description", 2023, genre.getId(), 18, 180, file.getId()));
        var savedFilm = sql2oFilmRepository.findById(film.getId()).get();
        assertThat(savedFilm).usingRecursiveComparison().isEqualTo(film);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var film1 = sql2oFilmRepository.save(new Film("name1", "description1", 2023, genre.getId(), 18, 180, file.getId()));
        var film2 = sql2oFilmRepository.save(new Film("name2", "description2", 2023, genre.getId(), 18, 180, file.getId()));
        var film3 = sql2oFilmRepository.save(new Film("name3", "description3", 2023, genre.getId(), 18, 180, file.getId()));
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(film1, film2, film3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oFilmRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oFilmRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var film = sql2oFilmRepository.save(new Film("name", "description", 2023, genre.getId(), 18, 180, file.getId()));
        sql2oFilmRepository.deleteById(film.getId());
        var findedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(findedFilm).isEqualTo(empty());
    }

}
