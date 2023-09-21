package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.repository.GenreRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oGenreRepository implements GenreRepository {
    private final Sql2o sql2o;
    private static final String INSERT_GENRE = "INSERT INTO genres (name) VALUES (:name)";
    private static final String FIND_BY_ID = "SELECT * FROM genres WHERE id = :id";
    private static final String FIND_BY_NAME = "SELECT * FROM genres WHERE name = :name";
    private static final String DELETE_BY_ID = "DELETE FROM genres WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM genres";
    private static final String FIND_ALL = "SELECT * FROM genres";

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Genre save(Genre genre) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_GENRE, true)
                    .addParameter("name", genre.getName());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            genre.setId(generatedId);
            return genre;
        }
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var genre = query.addParameter("id", id).executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_NAME);
            var genre = query.addParameter("name", name).executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
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
    public Collection<Genre> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.executeAndFetch(Genre.class);
        }
    }
}
