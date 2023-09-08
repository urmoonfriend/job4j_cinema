package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Ticket;

import java.util.Optional;

public interface TicketService {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int id);

    void deleteById(int id);
}
