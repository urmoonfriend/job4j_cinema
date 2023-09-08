package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    boolean update(User user);

    Optional<User> findById(int id);

    Optional<User> findByFullName(String name);

    void deleteById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
