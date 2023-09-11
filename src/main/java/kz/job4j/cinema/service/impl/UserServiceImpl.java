package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.User;
import kz.job4j.cinema.repository.UserRepository;
import kz.job4j.cinema.repository.impl.Sql2oUserRepository;
import kz.job4j.cinema.service.UserService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(Sql2oUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
