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
import ru.spmi.backend.dto.FilterDTO;
import ru.spmi.backend.entities.UsersEntity;
import ru.spmi.backend.repositories.UserRepository;
import ru.spmi.backend.services.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/")
    public ResponseEntity<?> getHomePage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "qwerty234");
        return new ResponseEntity<>("home page", headers, HttpStatus.OK);
    }
//
    @PostMapping("/test")
    public ResponseEntity<?> testMethod(@RequestBody String filters) {
        System.out.println("test page");
        System.out.println(filters);
        return new ResponseEntity<>(filters, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(userDAO.toSha1("test"));
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/success")
    public ResponseEntity<?> homeSuccess() {
        return new ResponseEntity<>(new ChosenRoleDTO("success"), HttpStatus.OK);
    }

}
