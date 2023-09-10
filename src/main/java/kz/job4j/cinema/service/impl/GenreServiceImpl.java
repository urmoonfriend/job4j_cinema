package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.repository.GenreRepository;
import kz.job4j.cinema.repository.impl.Sql2oGenreRepository;
import kz.job4j.cinema.service.GenreService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(Sql2oGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> findById(int id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Genre getById(int id) {
        var genreOpt = findById(id);
        if (genreOpt.isPresent()) {
            return genreOpt.get();
        }
        return new Genre("unknown");
    }
}
