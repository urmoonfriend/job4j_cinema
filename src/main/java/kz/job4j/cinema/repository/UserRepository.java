package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> findById(int id);

    Optional<User> findByFullName(String name);

    void deleteById(int id);

    void deleteAll();

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean update(User user);

    Collection<User> findAll();
}
