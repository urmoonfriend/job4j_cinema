package kz.job4j.cinema.model.entity;

import java.util.Objects;

public class File {
    private Integer id;
    private String name;
    private String path;

    public File() {
    }

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return id == file.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
