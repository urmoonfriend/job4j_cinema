package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.model.request.BuyRequest;

import java.util.Optional;

public interface TicketService {
    Ticket save(Ticket ticket);
    Optional<Ticket> isAlreadyExists(Integer sessionId, Integer rowNumber, Integer placeNumber);

    Optional<Ticket> findById(int id);

    void deleteById(int id);
    Ticket save(BuyRequest request);
}
