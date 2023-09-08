package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.FilmSession;

import java.util.Optional;

public interface FilmSessionService {
    Optional<FilmSession> findById(int id);

    FilmSession save(FilmSession filmSession);

    void deleteById(int id);
}
