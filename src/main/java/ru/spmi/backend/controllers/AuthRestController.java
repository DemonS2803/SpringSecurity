package ru.spmi.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.entities.AuthRequestDTO;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.exceptions.UserLoginNotFoundException;
import ru.spmi.backend.repositories.UserRepository;
import ru.spmi.backend.security.JwtTokenProvider;
import ru.spmi.backend.security.UserDetailsServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthRestController(AuthenticationManager authenticationManager,
                              UserRepository userRepository,
                              JwtTokenProvider jwtTokenProvider,
                              PasswordEncoder passwordEncoder,
                              @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
        System.out.println("hi from login");
        System.out.println(passwordEncoder.encode(request.getPassword()));
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            System.out.println("hi from login1");
            UserDetailsServiceImpl userService = (UserDetailsServiceImpl) userDetailsService.loadUserByUsername(request.getLogin());
            User user = userRepository.findUserByLogin(request.getLogin()).orElseThrow(() -> new UserLoginNotFoundException());
            System.out.println("hi from login2");
            String token = jwtTokenProvider.createToken(request.getLogin(), user.getRoles().name());
            System.out.println("hi from login3");
            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid passw or login", HttpStatus.FORBIDDEN);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
//        System.out.println("hi from login");
//        System.out.println(passwordEncoder.encode(request.getPassword()));
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
//            Authentication auth = authenticationManager.authenticate(authenticationToken);
//            UserDetailsServiceImpl userDetailsService
//        }catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid passw or login", HttpStatus.FORBIDDEN);
//        }
//    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hi from logout");
        var handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }
}
