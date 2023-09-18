package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    public void whenRequestGetFileByIdThenOk() throws Exception {
        byte[] bytes = Files.readAllBytes(Path.of("files/1plus1.jpg"));
        FileDto fileDto = new FileDto("1plus1.jpg", bytes);
        File file = new File("1plus1.jpg", "files/1plus1.jpg");
        when(fileRepository.findById(any(Integer.class))).thenReturn(Optional.of(file));
        var result = fileService.getFileById(any(Integer.class));
        assertThat(result.get().getName()).isEqualTo(fileDto.getName());
    }
}
