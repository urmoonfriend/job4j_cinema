package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.TicketService;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService sessionService;
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String NOT_FOUND_PAGE = "errors/404";
    private static final String NOT_FOUND_MESSAGE = "Билет с указанным идентификатором не найден";
    private static final String TICKET_ATTRIBUTE = "ticket";
    private static final String SESSION_ATTRIBUTE = "filmSession";
    private static final String SUCCESS_PAGE = "tickets/success";
    private static final String FAIL_PAGE = "tickets/fail";

    public TicketController(TicketServiceImpl ticketService, FilmSessionServiceImpl sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{ticketId}")
    public String getTicketPage(Model model, @PathVariable("ticketId") int ticketId, HttpServletRequest request) {
        var ticketOpt = ticketService.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute(TICKET_ATTRIBUTE, ticketOpt.get());
        return SUCCESS_PAGE;
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute BuyRequest buyRequest, Model model, HttpServletRequest request) {
        System.out.println("buyTicket method request : " + buyRequest.toString());
        try {
            var user = (User) request.getAttribute("user");
            var filmSession = sessionService.findById(buyRequest.getFilmSessionId());
            if (user == null || user.getId() == null) {
                model.addAttribute("buyRequest", buyRequest);
                return "users/login";
            } else if (filmSession.isPresent()
                    && ticketService.isAlreadyExists(buyRequest.getFilmSessionId(), buyRequest.getRowNumber(), buyRequest.getPlaceNumber())
                            .isEmpty()) {
                buyRequest.setUserId(user.getId());
                System.out.println("ticket not exists");
                model.addAttribute(SESSION_ATTRIBUTE, filmSession.get());
                model.addAttribute(TICKET_ATTRIBUTE, ticketService.save(buyRequest));
                return SUCCESS_PAGE;
            }
            return FAIL_PAGE;
        } catch (Exception exception) {
            model.addAttribute(MESSAGE_ATTRIBUTE, exception.getMessage());
            return NOT_FOUND_PAGE;
        }
    }

}
