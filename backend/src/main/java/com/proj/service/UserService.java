package com.proj.service;

import com.proj.form.UserCredentials;
import org.springframework.stereotype.Service;
import com.proj.domain.User;
import com.proj.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public User findByLogin(String login) {
        return login == null ? null : userRepository.findByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public User register(UserCredentials userCredentials) {
        if (userRepository.findByLogin(userCredentials.getLogin()) != null ||
                userRepository.findByTgId(userCredentials.getTgId()) != null ) {
            return null;
        }
        User users = new User();
        users.setLogin(userCredentials.getLogin());
        users.setTgId(userCredentials.getTgId());
        userRepository.save(users);
        userRepository.updatePasswordSha(users.getId(), userCredentials.getPassword());
        return users;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByTgId(String tgId) {
        return tgId == null ? null : userRepository.findByTgId(tgId);
    }
}
