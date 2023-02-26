package ru.spmi.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.repositories.UserRepository;
import ru.spmi.backend.services.UserService;

@RestController
@RequestMapping("/test")
public class TestController {

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @GetMapping("/users/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userRepository.findUserByLogin(login).get();
    }
}
