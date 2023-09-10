package kz.job4j.cinema.model.response;

import kz.job4j.cinema.model.entity.Hall;

import java.time.LocalDateTime;

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
}
