package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.AuthRequestDTO;
import ru.spmi.backend.dto.ChosenRoleDTO;
import ru.spmi.backend.entities.UsersEntity;
import ru.spmi.backend.repositories.UserRepository;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> getHomePage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>("home page",headers, HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testMethod(@RequestBody @Validated AuthRequestDTO authRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(new AuthRequestDTO("vasya", "qwerty"), headers, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/success")
    public ResponseEntity<?> homeSuccess() {
        return new ResponseEntity<>(new ChosenRoleDTO("success"), HttpStatus.OK);
    }

}
