package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.repository.HallRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oHallRepository implements HallRepository {
    private final Sql2o sql2o;
    private static final String INSERT_HALL = "INSERT INTO halls (name, row_count, place_count, description) "
            + "VALUES (:name, :row_count, :place_count, :description)";
    private static final String FIND_BY_ID = "SELECT * FROM halls WHERE id = :id";
    private static final String FIND_BY_NAME = "SELECT * FROM halls WHERE name = :name";
    private static final String DELETE_BY_ID = "DELETE FROM halls WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM halls";
    private static final String DELETE_ALL = "DELETE FROM halls";

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Hall save(Hall hall) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_HALL, true)
                    .addParameter("name", hall.getName())
                    .addParameter("row_count", hall.getRowCount())
                    .addParameter("place_count", hall.getPlaceCount())
                    .addParameter("description", hall.getDescription());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            hall.setId(generatedId);
            return hall;
        }
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var hall = query.addParameter("id", id).setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }

    @Override
    public Optional<Hall> findByName(String name) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_NAME);
            var hall = query.addParameter("name", name).setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID);
            query.addParameter("id", id).setColumnMappings(Hall.COLUMN_MAPPING).executeUpdate();
        }
    }

    @Override
    public Collection<Hall> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
        }
    }

    @Override
    public void deleteAll() {
        try (var connection = sql2o.open()) {
            connection.createQuery(DELETE_ALL).executeUpdate();
        }
    }
}
