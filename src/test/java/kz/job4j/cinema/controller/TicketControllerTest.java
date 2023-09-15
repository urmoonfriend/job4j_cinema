package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.model.response.SessionResponse;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketControllerTest {

    private TicketServiceImpl ticketService;
    private FilmSessionServiceImpl filmSessionService;
    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionServiceImpl.class);
        ticketService = mock(TicketServiceImpl.class);
        ticketController = new TicketController(ticketService, filmSessionService);
    }

    @Test
    public void whenRequestBuyThenOk() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmResponse filmResponse = new FilmResponse(
                "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        Hall hall = new Hall(0, "hall", 5, 5, "hall");
        SessionResponse sessionResponse1 = new SessionResponse(0, filmResponse, hall, dateTime, dateTime, 16);
        User user = new User(1, "fullName", "email@mail.ru", "password");
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", user);
        Ticket ticketBought = new Ticket(0, sessionResponse1.getId(), 4, 4, 1);
        BuyRequest buyRequest = new BuyRequest(sessionResponse1.getId(), 4, 4, 1);
        var model = new ConcurrentModel();
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.of(sessionResponse1));
        when(ticketService.isAlreadyExists(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(Optional.empty());
        when(ticketService.save(buyRequest)).thenReturn(ticketBought);
        var view = ticketController.buyTicket(buyRequest, model, request);
        var ticket = model.getAttribute("ticket");
        assertThat(view).isEqualTo("tickets/success");
        assertThat(ticket).isEqualTo(ticketBought);
    }

    @Test
    public void whenRequestBuyThenSessionNotFound() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmResponse filmResponse = new FilmResponse(
                "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        Hall hall = new Hall(0, "hall", 5, 5, "hall");
        SessionResponse sessionResponse1 = new SessionResponse(0, filmResponse, hall, dateTime, dateTime, 16);
        User user = new User(1, "fullName", "email@mail.ru", "password");
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", user);
        BuyRequest buyRequest = new BuyRequest(sessionResponse1.getId(), 4, 4, 1);
        var model = new ConcurrentModel();
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.empty());
        var view = ticketController.buyTicket(buyRequest, model, request);
        assertThat(view).isEqualTo("tickets/fail");
    }

    @Test
    public void whenRequestBuyThenTicketAlreadyBought() {
        var dateTime = now().truncatedTo(ChronoUnit.MINUTES);
        FilmResponse filmResponse = new FilmResponse(
                "film2", "film2", 2001, "genre2", 18, 180, 1
        );
        Hall hall = new Hall(0, "hall", 5, 5, "hall");
        SessionResponse sessionResponse1 = new SessionResponse(0, filmResponse, hall, dateTime, dateTime, 16);
        User user = new User(1, "fullName", "email@mail.ru", "password");
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", user);
        Ticket ticketBought = new Ticket(0, sessionResponse1.getId(), 4, 4, 1);
        BuyRequest buyRequest = new BuyRequest(sessionResponse1.getId(), 4, 4, 1);
        var model = new ConcurrentModel();
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.of(sessionResponse1));
        when(ticketService.isAlreadyExists(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(Optional.of(ticketBought));
        var view = ticketController.buyTicket(buyRequest, model, request);
        assertThat(view).isEqualTo("tickets/fail");
    }

}
