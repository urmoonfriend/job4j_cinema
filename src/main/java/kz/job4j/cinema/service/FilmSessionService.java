package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.model.response.SessionResponse;

import java.util.List;
import java.util.Optional;

public interface FilmSessionService {
    Optional<SessionResponse> findById(int id);

    FilmSession save(FilmSession filmSession);

    void deleteById(int id);

    List<SessionResponse> findAll();
}
