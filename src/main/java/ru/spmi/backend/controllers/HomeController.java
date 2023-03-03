package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.entities.UsersEntity;
import ru.spmi.backend.repositories.UserRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/success")
    public ResponseEntity<?> homeSuccess() {
        return new ResponseEntity<>("success home page", HttpStatus.OK);
    }}
