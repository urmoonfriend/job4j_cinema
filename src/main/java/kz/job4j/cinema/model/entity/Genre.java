package kz.job4j.cinema.model.entity;

import java.util.Objects;

public class Genre {
    private Integer id;
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return id == genre.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
