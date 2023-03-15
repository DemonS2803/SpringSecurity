package ru.spmi.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.dto.FilterDTO;

@RestController
@RequestMapping("/api/teacher")
public class TeacherPage {

    @GetMapping("/home")
    public ResponseEntity<?> teacherPage() {
        return new ResponseEntity<>(new FilterDTO("success teacher registration"), HttpStatus.OK);

    }
}
