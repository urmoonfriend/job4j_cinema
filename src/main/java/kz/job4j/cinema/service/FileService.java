package kz.job4j.cinema.service;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;

import java.util.Optional;

public interface FileService {
    File save(File file);
    File save(FileDto fileDto);
    Optional<File> findById(int id);
    Optional<FileDto> getFileById(int id);
    void deleteById(int id);
}
