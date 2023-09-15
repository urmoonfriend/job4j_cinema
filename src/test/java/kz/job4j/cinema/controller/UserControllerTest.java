package kz.job4j.cinema.controller;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserServiceImpl userService;
    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserServiceImpl.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestGetLoginPageThenOk() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestGetRegisterPageThenOk() {
        var view = userController.getRegistationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenRequestLogoutThenOk() {
        HttpSession httpSession = new MockHttpSession();
        var view = userController.logout(httpSession);
        assertThat(view).isEqualTo("redirect:/users/login");
    }

    @Test
    public void whenRequestLoginThenOk() {
        User userToLogin = new User(0, "fullName", "email@mail.ru", "password");
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.of(userToLogin));
        var model = new ConcurrentModel();
        var view = userController.loginUser(userToLogin, model, new MockHttpServletRequest());
        var user = model.getAttribute("user");
        assertThat(view).isEqualTo("redirect:/sessions");
        assertThat(user).isEqualTo(userToLogin);
    }

    @Test
    public void whenRequestLoginThenUserNotFound() {
        User userToLogin = new User(0, "fullName", "email@mail.ru", "password");
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = userController.loginUser(userToLogin, model, new MockHttpServletRequest());
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestRegisterThenOk() {
        User userToLogin = new User(0, "fullName", "email@mail.ru", "password");
        when(userService.save(userToLogin)).thenReturn(Optional.of(userToLogin));
        var model = new ConcurrentModel();
        var view = userController.register(model, userToLogin);
        var user = model.getAttribute("user");
        assertThat(view).isEqualTo("users/login");
        assertThat(user).isEqualTo(userToLogin);
    }

    @Test
    public void whenRequestRegisterThenNotSaved() {
        User userToLogin = new User(0, "fullName", "email@mail.ru", "password");
        when(userService.save(userToLogin)).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = userController.register(model, userToLogin);
        assertThat(view).isEqualTo("errors/404");
    }
}
