package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.repository.TicketRepository;
import kz.job4j.cinema.repository.impl.Sql2oTicketRepository;
import kz.job4j.cinema.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(Sql2oTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> isAlreadyExists(Integer sessionId, Integer rowNumber, Integer placeNumber) {
        return ticketRepository.findBySessionIdAndRowNumberAndPlaceNumber(sessionId, rowNumber, placeNumber);
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket save(BuyRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSessionId(request.getFilmSessionId());
        ticket.setPlaceNumber(request.getPlaceNumber());
        ticket.setRowNumber(request.getRowNumber());
        ticket.setUserId(request.getUserId());
        return ticketRepository.save(ticket);
    }
}
