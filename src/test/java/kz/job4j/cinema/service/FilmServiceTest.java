package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.model.response.FilmResponse;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.service.impl.FileServiceImpl;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import kz.job4j.cinema.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmServiceTest {
    private FilmServiceImpl filmService;
    private Sql2oFilmRepository filmRepository;
    private GenreServiceImpl genreService;
    private FileServiceImpl fileService;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileServiceImpl.class);
        filmRepository = mock(Sql2oFilmRepository.class);
        genreService = mock(GenreServiceImpl.class);
        filmService = new FilmServiceImpl(filmRepository, genreService, fileService);
    }

    @Test
    public void whenRequestSaveThenOk() {
        byte[] bytes = {1, 2, 3};
        FileDto fileDto = new FileDto("name", bytes);
        File file = new File("file", "path");
        file.setId(0);
        Film film = new Film("name", "description", 2000, 0, 16, 180, 0);
        when(fileService.save(fileDto)).thenReturn(file);
        when(filmRepository.save(film)).thenReturn(film);
        var result = filmService.save(film, fileDto);
        assertThat(result).isEqualTo(film);
    }

    @Test
    public void whenRequestGetByFilmThenOk() {
        Film film = new Film("name", "description", 2000, 0, 16, 180, 0);
        FilmResponse filmResponse = new FilmResponse("name", "description", 2000, "genre", 16, 180, 0);
        when(genreService.getById(0)).thenReturn(new Genre("genre"));
        var result = filmService.getByFilm(film);
        assertThat(result).isEqualTo(filmResponse);
    }

    @Test
    public void whenRequestGetByFilmThenGenreNotFound() {
        Film film = new Film("name", "description", 2000, 0, 16, 180, 0);
        FilmResponse filmResponse = new FilmResponse("name", "description", 2000, "unknown", 16, 180, 0);
        when(genreService.getById(0)).thenReturn(new Genre("unknown"));
        var result = filmService.getByFilm(film);
        assertThat(result).isEqualTo(filmResponse);
    }

    @Test
    public void whenRequestFindAllThenOk() {
        Film film = new Film("name", "description", 2000, 0, 16, 180, 0);
        Film film2 = new Film("name2", "description2", 2000, 1, 16, 180, 0);
        FilmResponse filmResponse = new FilmResponse("name", "description", 2000, "genre1", 16, 180, 0);
        FilmResponse filmResponse2 = new FilmResponse("name", "description", 2000, "genre2", 16, 180, 0);
        when(filmRepository.findAll()).thenReturn(List.of(film, film2));
        when(genreService.getById(0)).thenReturn(new Genre("genre1"));
        when(genreService.getById(1)).thenReturn(new Genre("genre2"));
        var result = filmService.findAll();
        assertThat(result).isEqualTo(List.of(filmResponse, filmResponse2));
    }

    @Test
    public void whenRequestFindByIdThenOk() {
        Film film = new Film("name", "description", 2000, 0, 16, 180, 0);
        FilmResponse filmResponse = new FilmResponse("name", "description", 2000, "genre1", 16, 180, 0);
        when(filmRepository.findById(0)).thenReturn(Optional.of(film));
        when(genreService.getById(0)).thenReturn(new Genre("genre1"));
        var result = filmService.findById(0);
        assertThat(result).isEqualTo(Optional.of(filmResponse));
    }

    @Test
    public void whenRequestFindByIdThenNotFound() {
        when(filmRepository.findById(0)).thenReturn(Optional.empty());
        when(genreService.getById(0)).thenReturn(new Genre("genre1"));
        var result = filmService.findById(0);
        assertThat(result).isEqualTo(Optional.empty());
    }
}
