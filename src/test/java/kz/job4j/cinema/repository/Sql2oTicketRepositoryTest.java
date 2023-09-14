package kz.job4j.cinema.repository;

import kz.job4j.cinema.configuration.DatasourceConfiguration;
import kz.job4j.cinema.model.entity.*;
import kz.job4j.cinema.repository.impl.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oHallRepository sql2oHallRepository;
    private static Sql2oGenreRepository sql2oGenreRepository;

    private static Sql2oUserRepository sql2oUserRepository;
    private static File file;
    private static Film film;
    private static Genre genre;
    private static Hall hall;

    private static User user;
    private static FilmSession filmSession;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);

        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        hall = sql2oHallRepository.save(new Hall(1, "hall", 5, 5, "hall"));
        file = sql2oFileRepository.save(new File("test", "test"));
        genre = sql2oGenreRepository.save(new Genre("drama"));
        film = sql2oFilmRepository.save(new Film("film", "description", 2023, genre.getId(), 18, 180, file.getId()));
        filmSession = sql2oFilmSessionRepository.save(new FilmSession(0, film.getId(), hall.getId(), dateTime, dateTime, 1000));
        user = sql2oUserRepository.save(new User(0, "user", "user@mail.ru", "password")).get();
    }

    @AfterEach
    public void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @AfterAll
    public static void deleteSaved() {
        sql2oTicketRepository.deleteAll();
        sql2oFilmSessionRepository.deleteAll();
        sql2oUserRepository.deleteAll();
        sql2oFilmRepository.deleteAll();
        sql2oFileRepository.deleteAll();
        sql2oHallRepository.deleteAll();
        sql2oGenreRepository.deleteAll();
    }

    @Test
    public void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, filmSession.getId(), 4, 4, user.getId()));
        var savedTicket = sql2oTicketRepository.findById(ticket.getId()).get();
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, filmSession.getId(), 5, 5, user.getId()));
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, filmSession.getId(), 6, 6, user.getId()));
        var ticket3 = sql2oTicketRepository.save(new Ticket(3, filmSession.getId(), 7, 7, user.getId()));
        var result = sql2oTicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oTicketRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var ticket = sql2oTicketRepository.save(new Ticket(4, filmSession.getId(), 7, 7, user.getId()));
        sql2oTicketRepository.deleteById(ticket.getId());
        var foundedTicket = sql2oTicketRepository.findById(ticket.getId());
        assertThat(foundedTicket).isEqualTo(empty());
    }

}
