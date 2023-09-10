package kz.job4j.cinema.controller;

import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String NOT_FOUND_PAGE = "errors/404";
    private static final String NOT_FOUND_MESSAGE = "Сеанс с указанным идентификатором не найден";
    private static final String SESSION_ATTRIBUTE = "filmSession";
    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionServiceImpl filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{sessionId}")
    public String getById(Model model, @PathVariable("sessionId") Integer sessionId) {
        var filmResponseOptional = filmSessionService.findById(sessionId);
        if (filmResponseOptional.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute(SESSION_ATTRIBUTE, filmResponseOptional.get());
        return "sessions/one";
    }

}
