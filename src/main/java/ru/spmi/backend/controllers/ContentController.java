package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.entities.UsersEntity;
import ru.spmi.backend.repositories.UserRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("permission-create")
    public ArrayList<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/success")
    @PreAuthorize("permission-read")
    public String getSuccessPage() {
        return "success";
    }
}
