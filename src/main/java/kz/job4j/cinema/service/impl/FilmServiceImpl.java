package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.repository.FilmRepository;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.service.FileService;
import kz.job4j.cinema.service.FilmService;
import kz.job4j.cinema.service.GenreService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class FilmServiceImpl implements FilmService {
    private final FileService fileService;
    private final FilmRepository filmRepository;
    private final GenreService genreService;

    public FilmServiceImpl(Sql2oFilmRepository filmRepository, GenreServiceImpl genreService, FileServiceImpl fileService) {
        this.filmRepository = filmRepository;
        this.genreService = genreService;
        this.fileService = fileService;
    }

    @Override
    public Film save(Film film, FileDto fileDto) {
        saveNewFile(film, fileDto);
        return filmRepository.save(film);
    }

    private void saveNewFile(Film film, FileDto image) {
        var file = fileService.save(image);
        film.setFileId(file.getId());
    }

    @Override
    public Optional<FilmResponse> findById(int id) {
        var filmOpt = filmRepository.findById(id);
        return filmOpt.map(this::getByFilm);
    }

    @Override
    public Optional<FilmResponse> findByName(String name) {
        var filmOpt = filmRepository.findByName(name);
        return filmOpt.map(this::getByFilm);
    }

    @Override
    public void deleteById(int id) {
        filmRepository.deleteById(id);
    }

    @Override
    public List<FilmResponse> findAll() {
        List<FilmResponse> resultList = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            resultList.add(getByFilm(film));
        }
        return resultList;
    }

    @Override
    public FilmResponse getByFilm(Film film) {
        return new FilmResponse(film.getId(), film.getName(), film.getDescription(), film.getYear(), genreService.getById(film.getGenreId()).getName(), film.getMinimalAge(), film.getDurationInMinutes(), film.getFileId());
    }
}
