package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Hall;

import java.util.Optional;

public interface HallService {
    Hall save(Hall hall);

    Optional<Hall> findById(int id);

    Optional<Hall> findByName(String name);

    void deleteById(int id);
}
