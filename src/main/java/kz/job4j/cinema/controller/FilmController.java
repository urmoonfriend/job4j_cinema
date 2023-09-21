package kz.job4j.cinema.controller;

import kz.job4j.cinema.service.FilmService;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ThreadSafe
@RequestMapping("/films")
public class FilmController {
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
            model.addAttribute("message", "Фильм с указанным идентификатором не найден");
            return "errors/404";
        }
        model.addAttribute("film", filmResponseOptional.get());
        return "films/one";
    }
}
