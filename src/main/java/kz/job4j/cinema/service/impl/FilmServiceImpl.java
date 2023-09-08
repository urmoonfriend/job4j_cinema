package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.repository.FilmRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.service.FilmService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(Sql2oFilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Optional<Film> findByName(String name) {
        return filmRepository.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        filmRepository.deleteById(id);
    }
}
