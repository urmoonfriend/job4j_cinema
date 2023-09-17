package kz.job4j.cinema.service;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.repository.impl.Sql2oUserRepository;
import kz.job4j.cinema.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserServiceImpl userService;
    private Sql2oUserRepository userRepository;

    @BeforeEach
    public void initServices() {
        userRepository = mock(Sql2oUserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void whenRequestSaveThenOk() {
        User user = new User(0, "fullName", "email", "password");
        when(userRepository.save(user)).thenReturn(Optional.of(user));
        var result = userService.save(user);
        assertThat(result).isEqualTo(Optional.of(user));
    }

    @Test
    public void whenRequestFindByIdThenOk() {
        User user = new User(0, "fullName", "email", "password");
        when(userRepository.findById(0)).thenReturn(Optional.of(user));
        var result = userService.findById(0);
        assertThat(result).isEqualTo(Optional.of(user));
    }

    @Test
    public void whenRequestFindByIdThenNotFound() {
        when(userRepository.findById(0)).thenReturn(Optional.empty());
        var result = userService.findById(0);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenRequestFindByEmailAndPasswordThenOk() {
        User user = new User(0, "fullName", "email", "password");
        when(userRepository.findByEmailAndPassword("email", "password")).thenReturn(Optional.of(user));
        var result = userService.findByEmailAndPassword("email", "password");
        assertThat(result).isEqualTo(Optional.of(user));
    }

    @Test
    public void whenRequestFindByEmailAndPasswordThenNotFound() {
        when(userRepository.findByEmailAndPassword("email", "password")).thenReturn(Optional.empty());
        var result = userService.findByEmailAndPassword("email", "password");
        assertThat(result).isEqualTo(Optional.empty());
    }
}
