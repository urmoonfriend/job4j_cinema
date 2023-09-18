package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.repository.impl.Sql2oTicketRepository;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceTest {
    private TicketServiceImpl ticketService;
    private Sql2oTicketRepository ticketRepository;

    @BeforeEach
    public void initServices() {
        ticketRepository = mock(Sql2oTicketRepository.class);
        ticketService = new TicketServiceImpl(ticketRepository);
    }

    @Test
    public void whenRequestSaveTicketThenOk() {
        Ticket ticket = new Ticket(0, 0, 4, 4, 0);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        var result = ticketService.save(ticket);
        assertThat(result).isEqualTo(ticket);
    }

    @Test
    public void whenRequestSaveBuyRequestThenOk() {
        BuyRequest buyRequest = new BuyRequest(0, 4, 4, 0);
        Ticket ticket = new Ticket();
        ticket.setSessionId(0);
        ticket.setPlaceNumber(4);
        ticket.setRowNumber(4);
        ticket.setUserId(0);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        var result = ticketService.save(buyRequest);
        assertThat(result).isEqualTo(ticket);
    }

    @Test
    public void whenRequestIsAlreadyExistsThenExists() {
        Ticket ticket = new Ticket(0, 0, 4, 4, 0);
        when(ticketRepository.findBySessionIdAndRowNumberAndPlaceNumber(0, 4, 4))
                .thenReturn(Optional.of(ticket));
        var result = ticketService.isAlreadyExists(0, 4, 4);
        assertThat(result).isEqualTo(Optional.of(ticket));
    }

    @Test
    public void whenRequestIsAlreadyExistsThenNotExists() {
        when(ticketRepository.findBySessionIdAndRowNumberAndPlaceNumber(0, 4, 4))
                .thenReturn(Optional.empty());
        var result = ticketService.isAlreadyExists(0, 4, 4);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenRequestFindByIdThenOk() {
        Ticket ticket = new Ticket(0, 0, 4, 4, 0);
        when(ticketRepository.findById(0)).thenReturn(Optional.of(ticket));
        var result = ticketService.findById(0);
        assertThat(result).isEqualTo(Optional.of(ticket));
    }

    @Test
    public void whenRequestFindByIdThenNotFound() {
        when(ticketRepository.findById(0)).thenReturn(Optional.empty());
        var result = ticketService.findById(0);
        assertThat(result).isEqualTo(Optional.empty());
    }

}
