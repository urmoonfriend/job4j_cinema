package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.model.response.FilmResponse;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    Film save(Film film, FileDto fileDto);

    Optional<FilmResponse> findById(int id);

    Optional<FilmResponse> findByName(String name);

    void deleteById(int id);

    List<FilmResponse> findAll();

    FilmResponse getByFilm(Film film);
}
