package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Genre;

import java.util.Optional;

public interface GenreService {
    Genre save(Genre genre);

    Optional<Genre> findById(int id);

    Optional<Genre> findByName(String name);

    void deleteById(int id);
    Genre getById(int id);
}
