package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.service.FilmService;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@ThreadSafe
@RequestMapping("/films")
public class FilmController {
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String NOT_FOUND_PAGE = "errors/404";
    private static final String NOT_FOUND_MESSAGE = "Фильм с указанным идентификатором не найден";
    private static final String FILM_ATTRIBUTE = "film";
    private final FilmService filmService;

    public FilmController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var filmResponseOptional = filmService.findById(id);
        if (filmResponseOptional.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute(FILM_ATTRIBUTE, filmResponseOptional.get());
        return "films/one";
    }
}
