package kz.job4j.cinema.model.response;

import kz.job4j.cinema.model.entity.Hall;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionResponse {

    private Integer id;

    private FilmResponse film;

    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;

    public SessionResponse() {
    }

    public SessionResponse(Integer id, FilmResponse film, Hall hall, LocalDateTime startTime, LocalDateTime endTime, Integer price) {
        this.id = id;
        this.film = film;
        this.hall = hall;
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

    public FilmResponse getFilm() {
        return film;
    }

    public void setFilm(FilmResponse film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
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
        SessionResponse sessionResponse = (SessionResponse) o;
        return id == sessionResponse.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[ {\n\tid: " + id + ",\n"
                + "\tfilm: " + film.toString() + ",\n"
                + "\thall: " + hall + ",\n"
                + "\tstartTime:" + startTime + ",\n"
                + "\tendTime: " + endTime + ",\n"
                + "\tprice: " + price + "\n} ]";
    }
}
