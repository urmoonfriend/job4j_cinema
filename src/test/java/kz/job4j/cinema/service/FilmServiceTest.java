package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.model.entity.Film;
import kz.job4j.cinema.repository.impl.Sql2oFilmRepository;
import kz.job4j.cinema.service.impl.FileServiceImpl;
import kz.job4j.cinema.service.impl.FilmServiceImpl;
import kz.job4j.cinema.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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
        
}
