package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.repository.UserRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;
    private static final String INSERT_USER = "INSERT INTO users (full_name, email, password) VALUES (:full_name, :email, :password) ";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = :id";
    private static final String FIND_BY_FULL_NAME = "SELECT * FROM users WHERE full_name = :full_name";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM users";
    private static final String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE email = :email and password = :password";
    private static final String UPDATE_USER_BY_ID = "UPDATE users SET full_name = :full_name, email = :email,password = :password WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM users";

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_USER, true)
                    .addParameter("full_name", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var user = query.addParameter("id", id).
                    setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_FULL_NAME);
            var user = query.addParameter("full_name", fullName).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
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
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_EMAIL_PASSWORD);
            query.addParameter("email", email);
            query.addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean update(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(UPDATE_USER_BY_ID)
                    .addParameter("full_name", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .addParameter("id", user.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }
}
