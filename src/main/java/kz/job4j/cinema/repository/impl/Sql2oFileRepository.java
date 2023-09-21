package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.repository.FileRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oFileRepository implements FileRepository {
    private final Sql2o sql2o;
    private static final String INSERT_FILE = "INSERT INTO files (name, path) VALUES (:name, :path)";
    private static final String FIND_BY_ID = "SELECT * FROM files WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM files WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM files";
    private static final String SELECT_ALL = "SELECT * FROM files";

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public File save(File file) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_FILE, true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
            return file;
        }
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
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
    public Collection<File> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SELECT_ALL);
            return query.executeAndFetch(File.class);
        }
    }
}
