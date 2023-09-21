package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.TicketService;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tickets")
@Slf4j
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService sessionService;

    public TicketController(TicketServiceImpl ticketService, FilmSessionServiceImpl sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute BuyRequest buyRequest, Model model, HttpServletRequest request) {
        log.info("buyTicket method request : " + buyRequest.toString());
        try {
            var user = (User) request.getAttribute("user");
            var filmSession = sessionService.findById(buyRequest.getFilmSessionId());
            if (filmSession.isEmpty()
                    || ticketService.isAlreadyExists(buyRequest.getFilmSessionId(), buyRequest.getRowNumber(), buyRequest.getPlaceNumber())
                    .isPresent()) {
                return "tickets/fail";
            }
            buyRequest.setUserId(user.getId());
            log.info("ticket not exists");
            model.addAttribute("filmSession", filmSession.get());
            model.addAttribute("ticket", ticketService.save(buyRequest));
            return "tickets/success";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

}
