package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.FilmSession;
import kz.job4j.cinema.repository.FilmSessionRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oFilmSessionRepository implements FilmSessionRepository {
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) "
                            + "VALUES (:film_id, :halls_id, :start_time, :end_time, :price) ", true)
                    .addParameter("film_id", filmSession.getFilmId())
                    .addParameter("halls_id", filmSession.getHallId())
                    .addParameter("start_time", filmSession.getStartTime())
                    .addParameter("end_time", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            filmSession.setId(generatedId);
            return filmSession;
        }
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            var filmSession = query.addParameter("id", id).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM film_sessions WHERE id = :id");
            query.addParameter("id", id).executeUpdate();
        }
    }
}
