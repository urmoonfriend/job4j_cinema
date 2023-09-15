package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileControllerTest {

    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenRequestThenNotFound() {
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.empty());
        var result = fileController.getById(any(Integer.class));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenRequestThenOk() {
        byte[] bytes = {1, 2, 3};
        FileDto fileDto = new FileDto("test", bytes);
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.of(fileDto));
        var result = fileController.getById(any(Integer.class));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(bytes);
    }
}
