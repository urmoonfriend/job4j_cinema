package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(int id);

    Optional<User> findByFullName(String name);

    void deleteById(int id);
}
