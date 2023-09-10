package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.model.response.SessionResponse;
import kz.job4j.cinema.repository.FilmSessionRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmSessionRepository;
import kz.job4j.cinema.service.FilmService;
import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.HallService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class FilmSessionServiceImpl implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final FilmService filmService;
    private final HallService hallService;

    public FilmSessionServiceImpl(Sql2oFilmSessionRepository filmSessionRepository, FilmServiceImpl filmService, HallServiceImpl hallService) {
        this.filmService = filmService;
        this.filmSessionRepository = filmSessionRepository;
        this.hallService = hallService;
    }

    @Override
    public Optional<SessionResponse> findById(int id) {
        var sessionOpt = filmSessionRepository.findById(id);
        return sessionOpt.map(this::getBySession);
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        return filmSessionRepository.save(filmSession);
    }

    @Override
    public void deleteById(int id) {
        filmSessionRepository.deleteById(id);
    }

    @Override
    public List<SessionResponse> findAll() {
        List<SessionResponse> resultList = new ArrayList<>();
        for (FilmSession session : filmSessionRepository.findAll()) {
            resultList.add(getBySession(session));
        }
        return resultList;
    }

    private SessionResponse getBySession(FilmSession session) {
        var filmOpt = filmService.findById(session.getFilmId());
        var hallOpt = hallService.findById(session.getHallId());
        if (filmOpt.isPresent() && hallOpt.isPresent()) {
            return new SessionResponse(session.getId(), filmOpt.get(), hallOpt.get(), session.getStartTime(), session.getEndTime(), session.getPrice());
        }
        return null;
    }
}
