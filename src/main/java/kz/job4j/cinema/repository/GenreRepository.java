package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.Genre;

import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);

    Optional<Genre> findById(int id);

    Optional<Genre> findByName(String name);

    void deleteById(int id);
}
