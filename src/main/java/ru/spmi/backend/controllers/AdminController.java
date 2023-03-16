package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.FilterDTO;
import ru.spmi.backend.services.UserDAO;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserDAO userDAO;


    @GetMapping("/page")
    public ResponseEntity<?> adminPage() {
        return new ResponseEntity<>(new FilterDTO("success login\nbro admin"), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> adminFilterAction(@RequestBody String filters) {
        System.out.println(filters);
        return new ResponseEntity<>(new Gson().toJson(userDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
    }
}
