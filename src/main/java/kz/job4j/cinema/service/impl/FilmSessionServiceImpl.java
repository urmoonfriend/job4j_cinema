package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.repository.FilmSessionRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmSessionRepository;
import kz.job4j.cinema.service.FilmSessionService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class FilmSessionServiceImpl implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    public FilmSessionServiceImpl(Sql2oFilmSessionRepository filmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        return filmSessionRepository.save(filmSession);
    }

    @Override
    public void deleteById(int id) {
        filmSessionRepository.deleteById(id);
    }
}
