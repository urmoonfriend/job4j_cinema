package kz.job4j.cinema.model.dto;

import java.util.Objects;

public class FileDto {
    private String name;

    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileDto fileDto = (FileDto) o;
        return name == fileDto.name && content == fileDto.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

}
