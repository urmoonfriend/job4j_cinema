package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.File;

import java.util.Optional;

public interface FileService {
    File save(File file);

    Optional<File> findById(int id);

    void deleteById(int id);
}
