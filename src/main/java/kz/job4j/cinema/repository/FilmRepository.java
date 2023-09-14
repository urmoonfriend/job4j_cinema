package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Film save(Film genre);

    Optional<Film> findById(int id);

    Optional<Film> findByName(String name);

    void deleteById(int id);

    void deleteAll();

    Collection<Film> findAll();
}
