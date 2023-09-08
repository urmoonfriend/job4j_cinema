package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.repository.GenreRepository;
import kz.job4j.cinema.repository.UserRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public User save(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users (full_name, email, password) VALUES (:full_name, :email, password)", true)
                    .addParameter("full_name", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return user;
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE id = :id");
            var user = query.addParameter("id", id).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE full_name = :full_name");
            var user = query.addParameter("full_name", fullName).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id");
            query.addParameter("id", id).executeUpdate();
        }
    }
}
