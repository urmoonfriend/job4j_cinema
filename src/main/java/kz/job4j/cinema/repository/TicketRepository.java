package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int id);

    void deleteById(int id);
}
