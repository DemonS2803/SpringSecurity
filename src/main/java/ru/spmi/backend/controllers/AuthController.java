package ru.spmi.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.*;
import ru.spmi.backend.entities.DRolesEntity;
import ru.spmi.backend.security.JwtUtils;
import ru.spmi.backend.services.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUserPage(@RequestBody @Validated AuthRequestDTO loginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Authentication authentication = authenticateUser(loginRequest.getLogin(), loginRequest.getPassword());
        String jwt = jwtUtils.generateJwtToken(loginRequest.getLogin(), userDAO.getRoleByLogin(loginRequest.getLogin()));
        System.out.println(jwt);
        System.out.println("SET_AUTH " + SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("USER: " + authentication.getName());
        System.out.println("token generated");
        System.out.println("NameFromToken: " + jwtUtils.getUserNameFromJwtToken(jwt));
        System.out.println("RoleFromToken: " + jwtUtils.getRoleFromToken(jwt));

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", jwt);
//
        Set<String> roles = userDAO.findAllUserRoles(userDAO.findUserByLogin(loginRequest.getLogin())).stream().map(x -> x.getRoleName()).collect(Collectors.toSet());
        var responcedto = new AuthResponceDTO();
        System.out.println(roles.size());
        var userdto = new UserDTO();
        userdto.setToken(jwt);
        responcedto.setUser(userdto);
        if (roles.size() < 2) {
            responcedto.setNeedToChooseRole(false);
            return new ResponseEntity<>(responcedto, HttpStatus.ACCEPTED);

        } else {
            responcedto.setNeedToChooseRole(true);
            responcedto.setRoles(roles);
            return new ResponseEntity<>(responcedto, HttpStatus.OK);
        }
    }

    @GetMapping("/choose_role")
    public String chooseRolePage() {
        return "return choose-role page";
    }

    @PostMapping("/choose_role")
    public ResponseEntity<?> gotChosenRolePage(@RequestHeader("Authorization") String token, @RequestBody @Validated ChosenRoleDTO chosenRole) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(token);
        boolean isTokenValid = jwtUtils.validateJwtToken(token);
        boolean isRoleAllowed = userDAO.checkUserRole(jwtUtils.getUserNameFromJwtToken(token), chosenRole.getRole());
        System.out.println(token);
        System.out.println("NameFromGotToken: " + jwtUtils.getUserNameFromJwtToken(token));
        System.out.println("RoleFromGotToken: " + jwtUtils.getRoleFromToken(token));
        System.out.println("isTokenValid " + isTokenValid);
        System.out.println("isRoleAllowed " + isRoleAllowed);
        if (isTokenValid && isRoleAllowed) {
            String newToken = jwtUtils.generateJwtToken(
                        userDAO.findNeedLoginByLoginAndRole(
                                SecurityContextHolder.getContext().getAuthentication().getName(),
                                chosenRole.getRole()),
                        chosenRole.getRole()
            );
            System.out.println("token generated");
            System.out.println(newToken);
            System.out.println("ChosenNameFromToken: " + jwtUtils.getUserNameFromJwtToken(newToken));
            System.out.println("ChosenRoleFromToken: " + jwtUtils.getRoleFromToken(newToken));
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println(userDAO.findNeedLoginByLoginAndRole(login, chosenRole.getRole()));
            System.out.println(userDAO.getPasswordByLogin(login));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (
                                                    userDAO.findNeedLoginByLoginAndRole(login, chosenRole.getRole()),
                                                    userDAO.getPasswordByLogin(login)
                                                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(new TokenDTO(newToken), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Something went wrong....", HttpStatus.FORBIDDEN);
    }

    public Authentication authenticateUser(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, userDAO.toSha1(password)));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
