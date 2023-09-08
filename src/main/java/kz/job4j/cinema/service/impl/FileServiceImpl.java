package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.repository.FileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.service.FileService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(Sql2oFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> findById(int id) {
        return fileRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        fileRepository.deleteById(id);
    }
}
