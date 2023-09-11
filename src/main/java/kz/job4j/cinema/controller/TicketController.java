package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.TicketService;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private static final String REDIRECT_SESSIONS = "redirect:/sessions";

    public TicketController(TicketServiceImpl ticketService, FilmSessionServiceImpl sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{ticketId}")
    public String getTicketPage(Model model, @PathVariable("ticketId") int ticketId) {
        var ticketOpt = ticketService.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute(TICKET_ATTRIBUTE, ticketOpt.get());
        return SUCCESS_PAGE;
    }

    //TODO add user from HttpRequestServlet
    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute BuyRequest request, Model model) {
        System.out.println("buyTicket method request : " + request.toString());
        request.setUserId(1);
        try {
            var filmSession = sessionService.findById(request.getFilmSessionId());
            if (filmSession.isPresent()
                    && ticketService.isAlreadyExists(request.getFilmSessionId(), request.getRowNumber(), request.getPlaceNumber())
                            .isEmpty()) {
                System.out.println("ticket not exists");
                model.addAttribute(SESSION_ATTRIBUTE, filmSession.get());
                model.addAttribute(TICKET_ATTRIBUTE, ticketService.save(request));
                return SUCCESS_PAGE;
            }
            return FAIL_PAGE;
        } catch (Exception exception) {
            model.addAttribute(MESSAGE_ATTRIBUTE, exception.getMessage());
            return NOT_FOUND_PAGE;
        }
    }

}
