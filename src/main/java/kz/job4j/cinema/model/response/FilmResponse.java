package kz.job4j.cinema.model.response;

import java.util.Objects;

public class FilmResponse {
    private Integer filmId;
    private String name;
    private String description;
    private Integer year;
    private String genre;
    private Integer minimalAge;
    private Integer durationInMinutes;
    private Integer fileId;

    public FilmResponse() {
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(Integer minimalAge) {
        this.minimalAge = minimalAge;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmResponse film = (FilmResponse) o;
        return filmId == film.filmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

    @Override
    public String toString() {
        return "[ {\n\tfilmId: " + filmId + ",\n"
                + "\tname: " + name + ",\n"
                + "\tdescription: " + description + ",\n"
                + "\tyear:" + year + ",\n"
                + "\tgenre: " + genre + ",\n"
                + "\tminimalAge: " + minimalAge + ",\n"
                + "\tdurationInMinutes: " + durationInMinutes + ",\n"
                + "\tfileId: " + fileId + "\n} ]";
    }
}
