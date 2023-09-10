package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.request.BuyRequest;
import kz.job4j.cinema.service.TicketService;
import kz.job4j.cinema.service.impl.TicketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String NOT_FOUND_PAGE = "errors/404";
    private static final String NOT_FOUND_MESSAGE = "Билет с указанным идентификатором не найден";
    private static final String TICKET_ATTRIBUTE = "ticket";
    private static final String SUCCESS_PAGE = "tickets/success";

    private static final String FAIL_PAGE = "tickets/fail";

    private static final String REDIRECT_SESSIONS = "redirect:/sessions";

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
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

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute BuyRequest request, Model model) {
        System.out.printf("buyTicket method request : {}\n", request.toString());
        System.out.println("userId: "+request.getUserId());
        System.out.println("placeNumber: "+request.getPlaceNumber());
        System.out.println("rowNumber: : "+request.getRowNumber());
        System.out.println("sessionId: " + request.getFilmSessionId());
        request.setUserId(1);
        request.setFilmSessionId(1);
        try {
            if (ticketService.isAlreadyExists(request.getFilmSessionId(), request.getRowNumber(), request.getPlaceNumber())
                    .isPresent()) {
                return FAIL_PAGE;
            }
            model.addAttribute(TICKET_ATTRIBUTE, ticketService.save(request));
            return REDIRECT_SESSIONS;
        } catch (Exception exception) {
            model.addAttribute(MESSAGE_ATTRIBUTE, exception.getMessage());
            return NOT_FOUND_PAGE;
        }
    }

}
