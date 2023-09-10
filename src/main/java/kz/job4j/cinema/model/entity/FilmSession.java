package kz.job4j.cinema.model.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSession {

    public static final Map<String, String> COLUMN_MAPPING = Map.of("id", "id", "film_id", "filmId", "halls_id", "hallId", "start_time", "startTime", "end_time", "endTime", "price", "price");
    private Integer id;
    private Integer filmId;
    private Integer hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;

    public FilmSession() {
    }

    public FilmSession(Integer id, Integer filmId, Integer hallId, LocalDateTime startTime, LocalDateTime endTime, Integer price) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession filmSession = (FilmSession) o;
        return id == filmSession.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
