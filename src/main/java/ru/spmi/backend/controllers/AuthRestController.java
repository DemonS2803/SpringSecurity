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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.entities.AuthRequestDTO;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.exceptions.UserLoginNotFoundException;
import ru.spmi.backend.repositories.UserRepository;
import ru.spmi.backend.security.JwtTokenProvider;
import ru.spmi.backend.security.SecurityUser;
import ru.spmi.backend.security.TokenService;
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
    private final TokenService tokenService;

    public AuthRestController(AuthenticationManager authenticationManager,
                              UserRepository userRepository,
                              JwtTokenProvider jwtTokenProvider,
                              PasswordEncoder passwordEncoder,
                              @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                              TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
        System.out.println("hi from login");
        System.out.println(userRepository.findUserByLogin(request.getLogin()).get().getUsername());
        System.out.println(passwordEncoder.encode(request.getPassword()));

        System.out.println("hi from login1");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

            User user = userRepository.findUserByLogin(request.getLogin()).get();
            System.out.println("hi from login2");
            String token = jwtTokenProvider.createToken(request.getLogin(), user.getRoles().name());
            System.out.println("hi from login3");
            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("responce is ready");
        return ResponseEntity.ok(response);

    }

    record LoginRequest(String username, String password) {};
    record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {};
    @PostMapping("/loggin")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("login in");
        System.out.println(userRepository.findUserByLoginAndPassword(request.username(), request.password()).get().getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username, request.password);
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        System.out.println("auth ok");
        SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(request.username);
        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new LoginResponse("User with email = "+ request.username + " successfully logined!"

                , access_token, refresh_token);
    }

    record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {};


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hi from logout");
        var handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }
}
