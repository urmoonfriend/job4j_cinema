package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.FilmSession;

import java.util.Optional;

public interface FilmSessionRepository {
    Optional<FilmSession> findById(int id);

    FilmSession save(FilmSession filmSession);

    void deleteById(int id);
}
