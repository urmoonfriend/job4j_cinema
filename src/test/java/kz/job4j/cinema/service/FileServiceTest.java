package kz.job4j.cinema.service;

import kz.job4j.cinema.controller.FileController;
import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.repository.FileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.setLenientDateParsing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileServiceTest {

    private FileServiceImpl fileService;
    private Sql2oFileRepository fileRepository;

    @BeforeEach
    public void initServices() {
        fileRepository = mock(Sql2oFileRepository.class);
        fileService = new FileServiceImpl(fileRepository, "storage");

    }

    @Test
    public void whenRequestGetFileByIdThenNotFound() {
        when(fileRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        var result = fileService.getFileById(any(Integer.class));
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenRequestGetFileByIdThenOk() {
        byte[] bytes = {1, 2, 3};
        FileDto fileDto = new FileDto("test", bytes);
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.of(fileDto));
        var result = fileService.getFileById(any(Integer.class));
        assertThat(result).isEqualTo(Optional.of(fileDto));
    }
}
