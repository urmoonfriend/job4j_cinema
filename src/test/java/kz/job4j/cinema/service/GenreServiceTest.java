package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.repository.impl.Sql2oGenreRepository;
import kz.job4j.cinema.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreServiceTest {

    private GenreService genreService;
    private Sql2oGenreRepository sql2oGenreRepository;

    @BeforeEach
    public void initServices() {
        sql2oGenreRepository = mock(Sql2oGenreRepository.class);
        genreService = new GenreServiceImpl(sql2oGenreRepository);
    }

    @Test
    public void whenRequestGetByIdThenGetDefault() {
        Genre genre = new Genre("unknown");
        when(sql2oGenreRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        var result = genreService.getById(any(Integer.class));
        assertThat(result).isEqualTo(genre);
    }

    @Test
    public void whenRequestGetFileByIdThenOk() {
        Genre genre = new Genre("unknown");
        when(sql2oGenreRepository.findById(any(Integer.class))).thenReturn(Optional.of(genre));
        var result = genreService.getById(any(Integer.class));
        assertThat(result).isEqualTo(genre);
    }
}
