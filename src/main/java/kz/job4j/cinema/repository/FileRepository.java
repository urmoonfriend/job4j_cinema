package kz.job4j.cinema.repository;

import kz.job4j.cinema.model.entity.File;

import java.util.Optional;

public interface FileRepository {
    File save(File file);

    Optional<File> findById(int id);

    void deleteById(int id);
}
