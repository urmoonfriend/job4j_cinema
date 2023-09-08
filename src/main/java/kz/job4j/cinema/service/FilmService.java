package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Film;

import java.util.Optional;

public interface FilmService {

    Film save(Film genre);

    Optional<Film> findById(int id);

    Optional<Film> findByName(String name);

    void deleteById(int id);
}
