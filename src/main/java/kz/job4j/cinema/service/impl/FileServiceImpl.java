package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.dto.FileDto;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.repository.FileRepository;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.service.FileService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
@ThreadSafe
public class FileServiceImpl implements FileService {
    private final String storageDirectory;
    private final FileRepository fileRepository;

    public FileServiceImpl(Sql2oFileRepository fileRepository,
                           @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File save(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        return fileRepository.save(new File(fileDto.getName(), path));
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
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
