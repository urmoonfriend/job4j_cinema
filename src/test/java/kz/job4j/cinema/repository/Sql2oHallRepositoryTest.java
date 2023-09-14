package kz.job4j.cinema.repository;

import kz.job4j.cinema.configuration.DatasourceConfiguration;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.repository.impl.Sql2oHallRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @AfterEach
    public void clearHalls() {
        var halls = sql2oHallRepository.findAll();
        for (var hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var hall = sql2oHallRepository.save(new Hall(0, "hall1", 1, 1, "hall1"));
        var savedHall = sql2oHallRepository.findById(hall.getId()).get();
        assertThat(savedHall).usingRecursiveComparison().isEqualTo(hall);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var hall1 = sql2oHallRepository.save(new Hall(1, "hall1", 1, 1, "hall1"));
        var hall2 = sql2oHallRepository.save(new Hall(2, "hall2", 2, 2, "hall2"));
        var hall3 = sql2oHallRepository.save(new Hall(3, "hall3", 3, 3, "hall3"));
        var result = sql2oHallRepository.findAll();
        assertThat(result).isEqualTo(List.of(hall1, hall2, hall3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oHallRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oHallRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var hall = sql2oHallRepository.save(new Hall(0, "hall1", 1, 1, "hall1"));
        sql2oHallRepository.deleteById(hall.getId());
        var findedHall = sql2oHallRepository.findById(hall.getId());
        assertThat(findedHall).isEqualTo(empty());
    }

}
