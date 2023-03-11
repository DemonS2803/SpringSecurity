package ru.spmi.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @GetMapping("/page")
    public ResponseEntity<?> adminPage() {
        return new ResponseEntity<>("success admin registration", HttpStatus.OK);
    }
}
