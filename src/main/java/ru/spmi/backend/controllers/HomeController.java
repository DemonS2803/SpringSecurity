package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import org.hibernate.dialect.PostgreSQLJsonJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
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
import ru.spmi.backend.repositories.TestRepository;
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
    private TestRepository testRepository;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/")
    public ResponseEntity<?> getHomePage() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }
//
    @PostMapping("/test")
    public ResponseEntity<?> testMethod(@RequestBody String filters) {

        return new ResponseEntity<>(filters, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        var arr = testRepository.paginationFunc("{\"filter_fio\":\"Ива\", \"filter_position\":\"\"}", 30, 0);
        for (var a : arr) {
            System.out.println(a.getFio());
        }
//        System.out.println("db test fun call(6) = " + testRepository.dbIncrement(6));
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/success")
    public ResponseEntity<?> homeSuccess() {
        return new ResponseEntity<>(new ChosenRoleDTO("success"), HttpStatus.OK);
    }

}
