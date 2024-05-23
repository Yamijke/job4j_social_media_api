package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User, Long> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
