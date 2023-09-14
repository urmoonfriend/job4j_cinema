package kz.job4j.cinema.repository;

import kz.job4j.cinema.configuration.DatasourceConfiguration;
import kz.job4j.cinema.model.entity.File;
import kz.job4j.cinema.model.entity.Genre;
import kz.job4j.cinema.repository.impl.Sql2oFileRepository;
import kz.job4j.cinema.repository.impl.Sql2oGenreRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @AfterEach
    public void clearHalls() {
        var files = sql2oFileRepository.findAll();
        for (var file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }
    }

    @AfterAll
    public static void deleteSaved() {
        sql2oFileRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Collection<File> files = sql2oFileRepository.findAll();
        files.stream().forEach(
                file -> {
                    System.out.println();
                    System.out.println("[");
                    System.out.println(file.getId());
                    System.out.println(file.getName());
                    System.out.println(file.getPath());
                    System.out.println("]");
                    System.out.println();
                }
        );
        assertThat(files.size()).isEqualTo(0);
    }

    @Test
    public void whenSaveThenGetSame() {
        var file = sql2oFileRepository.save(new File("file.jpg", "files/file.jpg"));
        var savedFile = sql2oFileRepository.findById(file.getId()).get();
        assertThat(savedFile).usingRecursiveComparison().isEqualTo(file);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var file1 = sql2oFileRepository.save(new File("file1.jpg", "files/file1.jpg"));
        var file2 = sql2oFileRepository.save(new File("file2.jpg", "files/file2.jpg"));
        var file3 = sql2oFileRepository.save(new File("file3.jpg", "files/file3.jpg"));
        var result = sql2oFileRepository.findAll();
        assertThat(result).isEqualTo(List.of(file1, file2, file3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oFileRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oFileRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var file = sql2oFileRepository.save(new File("file4.jpg", "files/file4.jpg"));
        sql2oFileRepository.deleteById(file.getId());
        var foundFile = sql2oFileRepository.findById(file.getId());
        assertThat(foundFile).isEqualTo(empty());
    }

}
