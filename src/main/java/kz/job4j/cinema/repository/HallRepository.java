package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.Hall;

import java.util.Collection;
import java.util.Optional;

public interface HallRepository {
    Hall save(Hall hall);

    Optional<Hall> findById(int id);

    Optional<Hall> findByName(String name);

    void deleteById(int id);

    Collection<Hall> findAll();

    void deleteAll();
}
