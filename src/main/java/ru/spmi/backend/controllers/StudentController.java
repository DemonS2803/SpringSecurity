package ru.spmi.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @GetMapping("/home")
    public ResponseEntity<?> studentHomePage() {
        return new ResponseEntity<>("У студентов стипуха маленькая, а так все отлично", HttpStatus.OK);
    }

}
