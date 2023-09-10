package kz.job4j.cinema.controller;

import kz.job4j.cinema.service.FilmSessionService;
import kz.job4j.cinema.service.impl.FilmSessionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionServiceImpl filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sessions", filmSessionService.findAll());
        return "sessions/list";
    }

}
