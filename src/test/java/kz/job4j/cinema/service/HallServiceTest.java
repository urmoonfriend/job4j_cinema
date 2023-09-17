package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.repository.impl.Sql2oHallRepository;
import kz.job4j.cinema.service.impl.HallServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HallServiceTest {

    private HallServiceImpl hallService;
    private Sql2oHallRepository hallRepository;

    @BeforeEach
    public void initServices() {
        hallRepository = mock(Sql2oHallRepository.class);
        hallService = new HallServiceImpl(hallRepository);
    }

    @Test
    public void whenRequestSaveThenOk() {
        Hall hall = new Hall(0, "hall", 4, 4, "desc");
        when(hallRepository.save(hall)).thenReturn(hall);
        var result = hallService.save(hall);
        assertThat(result).isEqualTo(hall);
    }

    @Test
    public void whenRequestFindByIdThenOk() {
        Hall hall = new Hall(0, "hall", 4, 4, "desc");
        when(hallRepository.findById(0)).thenReturn(Optional.of(hall));
        var result = hallService.findById(0);
        assertThat(result).isEqualTo(Optional.of(hall));
    }

    @Test
    public void whenRequestFindByIdThenNotFound() {
        when(hallRepository.findById(0)).thenReturn(Optional.empty());
        var result = hallService.findById(0);
        assertThat(result).isEqualTo(Optional.empty());
    }
}
