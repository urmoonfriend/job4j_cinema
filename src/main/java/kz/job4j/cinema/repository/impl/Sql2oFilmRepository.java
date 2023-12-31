package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.repository.FilmRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oFilmRepository implements FilmRepository {
    private final Sql2o sql2o;
    private static final String INSERT_FILM = "INSERT INTO films (name, description, \"year\", genre_id, minimal_age, duration_in_minutes, file_id) "
            + "VALUES (:name, :description, :year, :genre_id, :minimal_age, :duration_in_minutes, :file_id)";
    private static final String FIND_BY_ID = "SELECT * FROM films WHERE id = :id";
    private static final String FIND_BY_NAME = "SELECT * FROM films WHERE name = :name";
    private static final String DELETE_BY_ID = "DELETE FROM films WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM films";
    private static final String SELECT_ALL = "SELECT * FROM films";

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film save(Film film) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_FILM, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genre_id", film.getGenreId())
                    .addParameter("minimal_age", film.getMinimalAge())
                    .addParameter("duration_in_minutes", film.getDurationInMinutes())
                    .addParameter("file_id", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return film;
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var film = query.addParameter("id", id).setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Optional<Film> findByName(String name) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_NAME);
            var film = query.addParameter("name", name).setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID);
            query.addParameter("id", id).executeUpdate();
        }
    }

    @Override
    public void deleteAll() {
        try (var connection = sql2o.open()) {
            connection.createQuery(DELETE_ALL).executeUpdate();
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SELECT_ALL);
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
